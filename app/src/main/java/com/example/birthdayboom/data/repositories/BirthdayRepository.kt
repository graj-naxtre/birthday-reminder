package com.example.birthdayboom.data.repositories

import com.example.birthdayboom.data.database.models.GroupedUIBirthdayData
import com.example.birthdayboom.data.database.models.UIBirthdayData
import kotlinx.coroutines.flow.Flow

interface BirthdayRepository {

    fun fetchAllContacts(): Flow<List<UIBirthdayData>>

    fun getListOfContacts(): List<UIBirthdayData>

    suspend fun  fetchAllBirthdays(): Flow<List<GroupedUIBirthdayData>>

    suspend fun addBirthday(
        name: String, mobileNumber: String, birthdate: Long,
        reminderTime: String, note: String
    )

    suspend fun checkTodayBirthday(date: String): List<UIBirthdayData>

    suspend fun updateBirthdayNote(
        id: Int, name: String, mobileNumber: String,
        birthdate: String, reminderTime: String, note: String
    )

    suspend fun getPersonProfile(contactId: Int): UIBirthdayData

    suspend fun getUpcomingBirthdayToSchedule(): UIBirthdayData?
}