package com.example.birthdayboom.data.database.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateConverters {
    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): String? {
        return localDate?.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }
}