package com.example.birthdayboom.utility.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.birthdayboom.data.database.entity.BirthdayEntity
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.utility.notification.BirthdayNotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyBirthdayWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val birthdayRepository: BirthdayRepository,
    private val birthdayNotificationManager: BirthdayNotificationManager
) : CoroutineWorker(
    appContext = context,
    params = workerParams
) {
    override suspend fun doWork(): Result {
        birthdayRepository.fetchAllBirthdaysToday().forEach { birthdayEntity ->
            dropNotification(entity = birthdayEntity)
        }

        return Result.success()
    }

    private fun dropNotification(entity: BirthdayEntity) {
        birthdayNotificationManager.notifyUserAboutBirthday(entity = entity)
    }

    companion object {
        const val UNIQUE_WORKER_ID = "unique_birthday_reminder_id"
    }
}