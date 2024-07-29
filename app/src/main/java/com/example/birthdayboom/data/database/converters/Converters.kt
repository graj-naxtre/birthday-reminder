package com.example.birthdayboom.data.database.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?) : String {
        return date.toString()
    }
}