package com.example.birthdayboom.data.repositories

import com.example.birthdayboom.data.database.models.GroupedUIBirthdayData
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.ui.screens.contact.components.ContactCardInfo
import com.example.birthdayboom.ui.screens.home.BirthdayCardInfo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface BirthdayRepository {

    fun fetchAllContacts(): Flow<List<ContactCardInfo>>

    fun fetchAllBirthdays(): Flow<List<BirthdayCardInfo>>

    fun getListOfContacts(): List<UIBirthdayData>

    suspend fun addBirthday(
        name: String,
        mobileNumber: String,
        birthdate: LocalDate,
        note: String
    ) : Result<Unit>

    suspend fun checkTodayBirthday(date: String): List<UIBirthdayData>

    suspend fun updateBirthdayNote(
        id: Int, name: String, mobileNumber: String,
        birthdate: String, reminderTime: String, note: String
    )

    suspend fun getPersonProfile(contactId: Int): UIBirthdayData

    suspend fun getUpcomingBirthdayToSchedule(): UIBirthdayData?
}