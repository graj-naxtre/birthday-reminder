package com.example.birthdayboom.utils.permission

data class PermissionState(
    val notificationPermission: Boolean,
    val alarmPermission: Boolean,
) {
    companion object {
        fun initial() = PermissionState(notificationPermission = false, alarmPermission = false)
    }
}
