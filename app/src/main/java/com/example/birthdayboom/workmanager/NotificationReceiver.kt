package com.example.birthdayboom.workmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.example.birthdayboom.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotificationReceiver : BroadcastReceiver() {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresCharging(true)
        .build()

    override fun onReceive(context: Context, intent: Intent?) {
        val name = intent?.getStringExtra("name")
        val notificationId = intent?.getIntExtra("notification_id", 0)
        val notificationHelper = NotificationHelper(context)

        if (name != null && notificationId != null) {
            notificationHelper.notifyTheUser(personName = name, notificationId = notificationId)
        }
        scheduleNextNotification(context)
    }

    private fun scheduleNextNotification(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val workRequest =
                OneTimeWorkRequestBuilder<ReminderWorkManager>()
                    .setConstraints(constraints)
                    .addTag(ReminderWorkManager.REMINDER_WORKER_TAG)
                    .build()

            WorkManager.getInstance(context).enqueue(workRequest).await()
        }
    }
}