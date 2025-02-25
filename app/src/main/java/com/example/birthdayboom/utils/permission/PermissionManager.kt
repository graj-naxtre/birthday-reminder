package com.example.birthdayboom.utils.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Singleton

@Singleton
class PermissionManager {
    private val _permissionState = MutableStateFlow(PermissionState.initial())
    val permissionState = _permissionState.asStateFlow()

    fun isNotificationPermissionGranted(context: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED).let { value ->
                _permissionState.update {
                    it.copy(
                        notificationPermission = value
                    )
                }
            }
        } else {
            _permissionState.update {
                it.copy(
                    notificationPermission = true
                )
            }
        }
    }
}