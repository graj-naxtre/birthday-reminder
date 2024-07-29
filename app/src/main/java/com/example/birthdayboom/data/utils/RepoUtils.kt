package com.example.birthdayboom.data.utils

import com.example.birthdayboom.data.database.models.GroupedUIBirthdayData
import java.util.Calendar

fun rotateMonthsByCurrentMonth(list: List<GroupedUIBirthdayData>): List<GroupedUIBirthdayData> {
    val earlyList = mutableListOf<GroupedUIBirthdayData>()
    val laterList = mutableListOf<GroupedUIBirthdayData>()
    val currentMonthNumber = Calendar.getInstance().get(Calendar.MONTH)
    for (item in list) {
        if (item.monthNumber <= currentMonthNumber) {
            earlyList.add(item)
        } else {
            laterList.add(item)
        }
    }
    return laterList + earlyList
}

