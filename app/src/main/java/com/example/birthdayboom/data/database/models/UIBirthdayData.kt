package com.example.birthdayboom.data.database.models

import com.example.birthdayboom.data.database.mappers.Target

data class UIBirthdayData(
    val contactId: Int? = 0,
    val name: String = "Unknown",
    val mobileNumber: String = "xxxxxxxxxx",
    val birthdate: String = "00-00-0000",
    val reminderTime: String = "00:00",
    val note: String = "No Reminder"
) : Target
