package com.example.birthdayboom.utility.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.birthdayboom.utility.worker.DailyBirthdayWorker
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class PermissionDelegate : IPermissionDelegate {

    override fun redirectToSettings(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", activity.packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        activity.startActivity(intent)
    }

    override fun isNotificationPermissionGranted(activity: Activity): PermissionState {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED ->
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            android.Manifest.permission.POST_NOTIFICATIONS
                        )
                    )
                        PermissionState.SHOW_DIALOG
                    else
                        PermissionState.REDIRECT_SETTINGS

                else -> PermissionState.GRANTED
            }
        } else {
            PermissionState.GRANTED
        }
    }

    override fun scheduleDailyWorker(activity: Activity) {
        val now = LocalDateTime.now()
        val nextMidnight = now.toLocalDate().plusDays(1).atStartOfDay()
        val initialDelay = Duration.between(now, nextMidnight).toMillis()

        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<DailyBirthdayWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

        WorkManager.getInstance(activity.applicationContext)
            .enqueueUniquePeriodicWork(
                uniqueWorkName = DailyBirthdayWorker.UNIQUE_WORKER_ID,
                existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
                request = periodicWorkRequest
            )
    }
}