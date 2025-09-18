package com.example.birthdayboom.utils.permission

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings

interface IPermissionDelegate {

    fun redirectToSettings(activity: Activity)

    fun isNotificationPermissionGranted(activity: Activity) : PermissionState
}