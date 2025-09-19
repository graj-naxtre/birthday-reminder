package com.example.birthdayboom.data.repositories

import com.example.birthdayboom.data.database.entity.BirthdayEntity
import com.example.birthdayboom.ui.screens.contact.components.ContactCardInfo
import com.example.birthdayboom.ui.screens.home.BirthdayCardInfo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface BirthdayRepository {

    suspend fun addBirthday(
        name: String,
        mobileNumber: String,
        birthdate: LocalDate,
        note: String
    ) : Result<Unit>

    fun fetchAllContacts(): Flow<List<ContactCardInfo>>

    fun fetchAllBirthdays(): Flow<List<BirthdayCardInfo>>

    fun fetchAllBirthdaysToday() : List<BirthdayEntity>

    suspend fun setMockData(mockContacts: List<BirthdayEntity>) : Result<Unit>
}