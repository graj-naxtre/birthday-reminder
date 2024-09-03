package com.example.birthdayboom.utils

import android.annotation.SuppressLint
import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
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

fun delayInSeconds(date: String, hour: String, minute: String): Long {
    val todayDateTime = Calendar.getInstance()
    return todayDateTime.timeInMillis / 1000L
}

fun extractInitials(name: String): String {
    var initials = ""
    name.trim().split(" ").forEach { word ->
        initials += word[0]
    }

    if(initials.isEmpty()){
        initials = "${name[0]}"
    }
    
    return initials
}