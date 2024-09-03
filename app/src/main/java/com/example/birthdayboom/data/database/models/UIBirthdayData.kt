package com.example.birthdayboom.data.database.models

import com.example.birthdayboom.data.database.mappers.Target


data class UIBirthdayData(
    val contactId: Int? = 0,
    val name: String = "Unknown",
    val initialLetters: String = "U",
    val mobileNumber: String = "xxxxxxxxxx",
    val birthdateMillis: Long = System.currentTimeMillis(),
    val birthdateString: String = "Jan 01 in X days",
    val birthdate: String = "dd-mm-yyyy",
    val age: Int = 0,
    val reminderTime: String = "00:00",
    val note: String = "No Reminder"
) : Target
