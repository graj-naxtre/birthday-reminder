package com.example.birthdayboom.workmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.birthdayboom.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationHelper : NotificationHelper
    @Inject
    lateinit var reminderManager: ReminderManager

    override fun onReceive(context: Context, intent: Intent?) {
        val name = intent?.getStringExtra("name")
        val notificationId = intent?.getIntExtra("notification_id", 0)

        if(name != null && notificationId != null){
            notificationHelper.notifyTheUser(personName = name, notificationId = notificationId)
        }

        scheduleNextNotification()
    }

    private fun scheduleNextNotification(){
        reminderManager.setReminderNotification()
    }
}