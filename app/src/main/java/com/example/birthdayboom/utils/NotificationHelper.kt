package com.example.birthdayboom.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.birthdayboom.R
import com.example.birthdayboom.ui.activities.MainActivity
import javax.inject.Inject

class NotificationHelper(
    private val context: Context,
) {
    private val CHANNEL_ID = "birthday_reminder"

    init {
        createNotificationChannel()
    }

    private fun buildNotification(personName: String): Notification {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("It's $personName Birthday !!!")
            .setContentText("Let's go and wish them...")
            .setSmallIcon(R.drawable.filled_cake_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setAutoCancel(true)
            .addAction(0, "Tap to wish", pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Birthday Reminder",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Reminds about birthday"
                }
                val nManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                nManager.createNotificationChannel(channel)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "notification failure", Toast.LENGTH_SHORT).show()
        }
    }

    fun notifyTheUser(personName: String, notificationId: Int) {
        if(!checkPermission()){ // if not granted then return
            Toast.makeText(context, "Permission required", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val nManager = NotificationManagerCompat.from(context)
            nManager.notify(notificationId, buildNotification(personName))
        } catch (e: SecurityException) {
            Toast.makeText(context, "notification failure", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission(): Boolean {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}