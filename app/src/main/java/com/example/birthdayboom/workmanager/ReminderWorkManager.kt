package com.example.birthdayboom.workmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.birthdayboom.data.PreferenceKey
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.ui.screens.contact.utils.DateUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar
import kotlin.coroutines.cancellation.CancellationException

@HiltWorker
class ReminderWorkManager @AssistedInject constructor(
    @Assisted  context: Context,
    @Assisted  workerParams: WorkerParameters,
    private val birthdayRepository: BirthdayRepository,
    private val sharedPreferences: SharedPreferences
) : CoroutineWorker(context, workerParams) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val alarmManagerRequestCode = 777

    companion object {
        const val REMINDER_WORKER_TAG = "birthday_notification_work"
    }

    override suspend fun doWork(): Result {
        return try {
            setReminderNotification()
            Result.success()
        } catch (e: CancellationException) {
            // Coroutine was cancelled, usually not a failure but a controlled termination
            Log.e("ReminderWorkManager", "Task was cancelled: ${e.localizedMessage}")
            e.printStackTrace()
            Result.retry()  // Or Result.failure(), depending on your needs
        } catch (e: Exception) {
            Log.e("ReminderWorkManager", "Error during work: ${e.localizedMessage}")
            e.printStackTrace()

            val outputData = Data.Builder()
                .putString("error_message", e.localizedMessage ?: "Unknown error")
                .putString("error_type", e.javaClass.simpleName)
                .build()

            Result.failure(outputData)
        }
    }

    private suspend fun setReminderNotification() {
        try {
            val birthdayList = birthdayRepository.getListOfContacts()

            val dateUtils = DateUtils()
            val upcomingBirthday = dateUtils.findClosestUpcomingDate(birthdayList)
                ?: throw Exception("No closest birthday found")

            scheduleUpcomingBirthdayNotification(birthday = upcomingBirthday)
        } catch (e: Exception) {
            Log.e(
                "ReminderWorkManager",
                "error while settingReminder: ${e.localizedMessage} ${e.message}"
            )
            throw e
        } finally {
            Log.d(
                "ReminderWorkManager",
                "setReminderNotification complete"
            )
        }
    }

    private fun scheduleUpcomingBirthdayNotification(birthday: UIBirthdayData) {
        try {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = birthday.birthdateMillis
                set(Calendar.YEAR, get(Calendar.YEAR))
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 1)
                set(Calendar.SECOND, 0)
            }

            birthday.contactId?.let { contactId ->
                val intent = Intent(this.applicationContext, NotificationReceiver::class.java).apply {
                    putExtra("notification_id", contactId)
                    putExtra("name", birthday.name)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    this.applicationContext,
                    alarmManagerRequestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                cancelPreviouslySetAlarm()

                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )

                sharedPreferences.edit {
                    putInt(PreferenceKey.LAST_NOTIFICATION_ID, contactId)
                    putString(PreferenceKey.LAST_NOTIFICATION_NAME, birthday.name)
                }
            } ?: throw Exception("contactId Not Found")
        } catch (e: Exception) {
            Log.e(
                "ReminderWorkManager",
                "scheduleUpcomingBirthdayNotification: ${e.localizedMessage} ${e.message}"
            )
            throw e
        } finally {
            Log.d(
                "ReminderWorkManager",
                "scheduleUpcomingBirthdayNotification complete"
            )
        }
    }

    private fun cancelPreviouslySetAlarm() {
        try {
            val notificationId =
                sharedPreferences.getInt(PreferenceKey.LAST_NOTIFICATION_ID, Int.MIN_VALUE)
            val notificationName =
                sharedPreferences.getString(PreferenceKey.LAST_NOTIFICATION_NAME, null)

            if (notificationId != Int.MIN_VALUE && notificationName != null) {
                val intent = Intent(this.applicationContext, NotificationReceiver::class.java).apply {
                    putExtra("notification_id", notificationId)
                    putExtra("name", notificationName)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    this.applicationContext,
                    alarmManagerRequestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.cancel(pendingIntent)
            } else {
                Log.i("ReminderWorkManager", "Empty values for previous alarms")
            }
        } catch (e: Exception) {
            Log.e(
                "ReminderWorkManager",
                "cancelPreviouslySetAlarm error: ${e.localizedMessage} ${e.message}"
            )
            throw e
        } finally {
            Log.d(
                "ReminderWorkManager",
                "cancelPreviouslySetAlarm complete"
            )
        }
    }
}