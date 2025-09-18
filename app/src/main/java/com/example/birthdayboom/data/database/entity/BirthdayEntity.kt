package com.example.birthdayboom.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.ui.screens.contact.components.ContactCardInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "birthdays")
data class BirthdayEntity(
    @PrimaryKey(autoGenerate = true)
    val contactId: Int = 0,
    @ColumnInfo(name = "NAME")
    val name: String,
    @ColumnInfo(name = "MOBILE_NUMBER")
    val mobileNumber: String,
    @ColumnInfo(name = "BIRTHDATE")
    val birthdate: LocalDate,
    @ColumnInfo(name = "BIRTH_MONTH")
    val birthdayMonth: Int,
    @ColumnInfo(name = "NOTE")
    val note: String?
){
    fun toUIBirthdayData(
        initialLetters: String = "A.S",
        birthdateMillis: Long = 0L,
        birthdateString: String = "",
        birthdate: String = "",
        age: Int = 0,
        reminderTime: String = "",
    ) = UIBirthdayData(
        contactId = contactId,
        name = name,
        initialLetters = initialLetters,
        mobileNumber = mobileNumber,
        birthdateMillis = birthdateMillis,
        birthdateString = birthdateString,
        birthdate = birthdate,
        age = age,
        reminderTime = reminderTime,
        note = note ?: ""
    )

    fun toContactCardInfo(formatter: DateTimeFormatter) : ContactCardInfo {

      return ContactCardInfo(
          contactId = contactId,
          name = name,
          initials = name.trim().split(" ").joinToString("") { it[0].toString() },
          date = birthdate.format(formatter)
      )
    }
}