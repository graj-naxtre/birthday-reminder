package com.example.birthdayboom.ui.providers

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

val LocalComponentDisplay = staticCompositionLocalOf { ComponentDisplayState() }

@Stable
class ComponentDisplayState {
    var showFloatingActionButton by mutableStateOf(false)
        private set

    var showBottomBar by mutableStateOf(true)
        private set

    var showBottomMenu by mutableStateOf(false)
        private set

    fun displayFloatingButton(value: Boolean){
        showFloatingActionButton = value
    }

    fun displayBottomBar(value: Boolean){
        showBottomBar = value
    }

    fun displayBottomSheet(value: Boolean){
        showBottomMenu = value
    }
}