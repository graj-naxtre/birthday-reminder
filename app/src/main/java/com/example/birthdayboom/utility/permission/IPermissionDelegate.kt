package com.example.birthdayboom.utility.permission

import android.app.Activity

interface IPermissionDelegate {

    fun redirectToSettings(activity: Activity)

    fun isNotificationPermissionGranted(activity: Activity) : PermissionState

    fun scheduleDailyWorker(activity: Activity)
}