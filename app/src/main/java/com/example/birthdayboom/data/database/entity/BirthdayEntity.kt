package com.example.birthdayboom.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birthdays")
data class BirthdayEntity(
    @PrimaryKey(autoGenerate = true)
    val contactId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "mobile_no")
    val mobileNumber: String,
    @ColumnInfo(name = "birthdate")
    val birthdate: String,
    @ColumnInfo(name = "reminder_time")
    val reminderTime: String,
    @ColumnInfo(name = "note")
    val note: String
)