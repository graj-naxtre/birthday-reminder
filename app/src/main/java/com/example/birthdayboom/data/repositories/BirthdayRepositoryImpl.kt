package com.example.birthdayboom.data.repositories

import android.util.Log
import com.example.birthdayboom.data.database.dao.BirthdayEntityDao
import com.example.birthdayboom.data.database.mappers.BirthdayBiMapper
import com.example.birthdayboom.data.database.models.GroupedUIBirthdayData
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.data.utils.rotateMonthsByCurrentMonth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BirthdayRepositoryImpl @Inject constructor(
    private val birthdayEntityDao: BirthdayEntityDao,
    private val birthdayBiMapper: BirthdayBiMapper
) : BirthdayRepository {

    override suspend fun addBirthday(
        name: String, mobileNumber: String, birthdate: String,
        reminderTime: String, note: String
    ) {
        birthdayEntityDao.addBirthday(
            birthdayBiMapper.convert(
                UIBirthdayData(
                    contactId = null,
                    name = name,
                    mobileNumber = mobileNumber,
                    birthdate = birthdate,
                    reminderTime = reminderTime,
                    note = note
                )
            )
        )
    }

    override fun fetchAllContacts(): Flow<List<UIBirthdayData>> {
        return birthdayEntityDao.fetchAllBirthdays().map { birthdayEntities ->
            birthdayEntities.map { birthdayBiMapper.convert(it) }.sortedBy { it.name }
        }
    }

    override fun fetchAllBirthdays(): Flow<List<GroupedUIBirthdayData>> {
        val months = listOf(
            "JANUARY", "FEBRUARY", "MARCH", "APRIL",
            "MAY", "JUNE", "JULY", "AUGUST",
            "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        )
        return birthdayEntityDao.fetchAllBirthdays().map { birthdayEntities ->
            birthdayEntities
                .map { birthdayBiMapper.convert(it) }
                .groupBy { it.birthdate.split("-")[1] }
                .map {
                    GroupedUIBirthdayData(
                        monthName = months[it.key.toInt() - 1],
                        monthNumber = it.key.toInt(),
                        birthdayList = it.value
                    )
                }
                .sortedBy { it.monthNumber }
                .let { rotateMonthsByCurrentMonth(it) }
        }
    }

    override suspend fun checkTodayBirthday(date: String): List<UIBirthdayData> {
        Log.d("today date", date)
        return birthdayEntityDao.checkTodayBirthday(date = date)
            .map { birthdayBiMapper.convert(it) }
    }

    override suspend fun updateBirthdayNote(
        id: Int,
        name: String,
        mobileNumber: String,
        birthdate: String,
        reminderTime: String,
        note: String
    ) {
        birthdayEntityDao.updateBirthdayNote(
            id,
            name,
            mobileNumber,
            birthdate,
            reminderTime,
            note
        )
    }

    override suspend fun getPersonProfile(contactId: Int): UIBirthdayData {
        return birthdayEntityDao.getPersonProfile(id = contactId)
            .let { birthdayBiMapper.convert(it) }
    }
}