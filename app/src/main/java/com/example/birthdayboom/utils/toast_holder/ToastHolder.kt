package com.example.birthdayboom.utils.toast_holder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ToastHolder {
    var toastMessage by mutableStateOf<String>("")
        private set

    fun displayToast(message: String){
        toastMessage = message
    }
}