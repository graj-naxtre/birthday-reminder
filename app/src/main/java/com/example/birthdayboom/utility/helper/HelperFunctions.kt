package com.example.birthdayboom.utility.helper

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.isValidPhoneNumber() : Boolean {
    val regex = "^[0-9]{10}$".toRegex()
    return this.matches(regex)
}

fun getTodayDate(): String {
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return dateFormatter.format(Date())
}

fun getMillisToDate(selectedDateMillis: Long?): String {
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()
    if (selectedDateMillis != null) {
        calendar.timeInMillis = selectedDateMillis
    }
    return dateFormatter.format(calendar.time)
}