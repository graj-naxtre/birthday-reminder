package com.example.birthdayboom.utils.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


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


}