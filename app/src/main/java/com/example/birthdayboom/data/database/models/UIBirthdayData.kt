package com.example.birthdayboom.data.database.models

import com.example.birthdayboom.data.database.mappers.Target
import java.util.Date

data class UIBirthdayData(
    val contactId: Int?,
    val name: String,
    val mobileNumber: String,
    val birthdate: String,
    val reminderTime: String,
    val note: String
): Target
