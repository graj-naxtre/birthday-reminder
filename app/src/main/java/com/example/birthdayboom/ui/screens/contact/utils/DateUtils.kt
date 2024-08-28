package com.example.birthdayboom.ui.screens.contact.utils

import com.example.birthdayboom.data.database.models.UIBirthdayData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class DateUtils {
    private val birthdayCalendar: Calendar = Calendar.getInstance()
    private val currentCalendar: Calendar = Calendar.getInstance()
    private val dateFormatter = SimpleDateFormat("MMM. dd", Locale.getDefault())

    init {
        currentCalendar.setTimeInMillis(System.currentTimeMillis())
    }

    suspend fun convertDate(timeInMillis: Long): String {

        birthdayCalendar.setTimeInMillis(timeInMillis)

        val differenceInMillis: Long
        val daysDifference: Int
        val formattedDate: String
        if (birthdayCalendar.get(Calendar.DAY_OF_YEAR) >= currentCalendar.get(Calendar.DAY_OF_YEAR)) {
            birthdayCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR))
            differenceInMillis = birthdayCalendar.timeInMillis - currentCalendar.timeInMillis
            formattedDate = dateFormatter.format(birthdayCalendar.timeInMillis)
            daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis).toInt()
        } else {
            birthdayCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR) + 1)
            differenceInMillis = birthdayCalendar.timeInMillis - currentCalendar.timeInMillis
            formattedDate = dateFormatter.format(birthdayCalendar.timeInMillis)
            daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis).toInt()
        }
        return if (daysDifference > 0) {
            "$formattedDate is Today"
        } else {
            "$formattedDate in $daysDifference days"
        }
    }

    suspend fun findClosestUpcomingDate(birthdays: List<UIBirthdayData>): UIBirthdayData? {
        val currentYear = currentCalendar.get(Calendar.YEAR)
        val currentTimeInMillis = System.currentTimeMillis()

        return birthdays
            .map { birthday ->
                // Replace the year with the current year
                birthdayCalendar.apply {
                    timeInMillis = birthday.birthdateMillis
                    set(Calendar.YEAR, currentYear)
                }
                birthday.copy(birthdateMillis = birthdayCalendar.timeInMillis)
            }
            .filter { it.birthdateMillis > currentTimeInMillis } // Keep only future events
            .minByOrNull { it.birthdateMillis } // Find the closest future event
    }
}