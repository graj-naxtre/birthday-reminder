package com.example.birthdayboom.workmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.ui.screens.contact.utils.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ReminderManagerImpl @Inject constructor(
    val context: Context,
    private val birthdayRepository: BirthdayRepository
) : ReminderManager {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setReminderNotification() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                birthdayRepository.fetchAllContacts().collectLatest { birthdayList ->
                    val dateUtils = DateUtils()
                    dateUtils.findClosestUpcomingDate(birthdayList)?.let { birthday ->
                        scheduleUpcomingBirthdayNotification(birthday = birthday)
                    }
                }
            }
        } catch (e: Exception){
            Log.d("ReminderManagerImpl", "error while settingReminder: ${e.localizedMessage}")
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

           val intent = Intent(context, NotificationReceiver::class.java).apply {
               putExtra("notification_id", birthday.contactId)
               putExtra("name", birthday.name)
           }

           val pendingIntent = PendingIntent.getBroadcast(
               context,
               birthday.contactId ?: 0,
               intent,
               PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
           )

           alarmManager.set(
               AlarmManager.RTC_WAKEUP,
               calendar.timeInMillis,
               pendingIntent
           )
       } catch (e: Exception){
           Log.d("ReminderManagerImpl", "error while scheduling: ${e.localizedMessage}")
       }
    }
}