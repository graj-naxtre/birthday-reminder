package com.example.birthdayboom.utility.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.birthdayboom.R
import com.example.birthdayboom.data.database.entity.BirthdayEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BirthdayNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val birthdayChannelId = "birthday_channel"
    private val birthdayChannelName = "Birthday Reminder"

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() = runCatching {
        val channel =
            NotificationChannel(
                birthdayChannelId,
                birthdayChannelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminds you about birthdays"
            }

        notificationManager.createNotificationChannel(channel)
    }

    fun notifyUserAboutBirthday(entity: BirthdayEntity) = runCatching {
        val notification = Notification.Builder(context, birthdayChannelId)
            .setContentTitle("It's ${entity.name} birthday today !!")
            .setContentText(entity.note ?: "Let's wish them a happy birthday.")
            .setSmallIcon(R.drawable.filled_cake_24)
            .setOngoing(true)
            .build()

        notificationManager.notify(entity.contactId, notification)
    }
}