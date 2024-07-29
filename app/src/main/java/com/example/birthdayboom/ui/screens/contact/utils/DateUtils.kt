package com.example.birthdayboom.ui.screens.contact.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun convertDate(inputDate: String): String {
    // Define the input and output date formatters
    val inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd", Locale.ENGLISH)

    // Parse the input date string to a LocalDate object
    val date = LocalDate.parse(inputDate, inputFormatter)
    // Format the LocalDate object to the desired output format
    return date.format(outputFormatter)
}