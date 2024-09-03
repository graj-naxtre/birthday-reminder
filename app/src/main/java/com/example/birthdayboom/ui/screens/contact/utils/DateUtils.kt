package com.example.birthdayboom.ui.screens.contact.utils

import com.example.birthdayboom.data.database.models.UIBirthdayData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class DateUtils {
    private val birthdayCalendar: Calendar = Calendar.getInstance()
    private val currentCalendar: Calendar = Calendar.getInstance()
    private val dateFormatter = SimpleDateFormat("MMM. dd", Locale.getDefault())

    init {
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0)
        currentCalendar.set(Calendar.MINUTE, 0)
        currentCalendar.set(Calendar.SECOND, 0)
    }

    fun convertDate(timeInMillis: Long): String {

        birthdayCalendar.setTimeInMillis(timeInMillis)

        val differenceInMillis: Long =
            if (birthdayCalendar.get(Calendar.DAY_OF_YEAR) >= currentCalendar.get(Calendar.DAY_OF_YEAR)) {
                // upcoming birthday
                birthdayCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR))
                birthdayCalendar.timeInMillis - currentCalendar.timeInMillis
            } else {
                // birthday passed
                birthdayCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR) + 1)
                birthdayCalendar.timeInMillis - currentCalendar.timeInMillis
            }

        val formattedDate: String = dateFormatter.format(birthdayCalendar.timeInMillis)
        val daysDifference: Int = TimeUnit.MILLISECONDS.toDays(differenceInMillis).toInt()

        return if (daysDifference > 0) {
            "$formattedDate in $daysDifference days"
        } else {
            "$formattedDate is Today"
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

    fun convertMillisToDate(dateInMillis: Long): Pair<String, Int> {
        birthdayCalendar.timeInMillis = dateInMillis
        val birthdate = SimpleDateFormat(
            "dd-MM-yyyy",
            Locale.getDefault()
        ).format(birthdayCalendar.timeInMillis)

        val age =
            ((TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()) - TimeUnit.MILLISECONDS.toDays(dateInMillis)) / 365).toInt()

        return Pair(birthdate, age)
    }
}