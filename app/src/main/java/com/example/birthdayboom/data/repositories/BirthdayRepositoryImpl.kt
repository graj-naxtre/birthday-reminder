package com.example.birthdayboom.data.repositories

import android.util.Log
import com.example.birthdayboom.data.database.dao.BirthdayEntityDao
import com.example.birthdayboom.data.database.entity.BirthdayEntity
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.ui.screens.contact.components.ContactCardInfo
import com.example.birthdayboom.ui.screens.home.BirthdayCardInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class BirthdayRepositoryImpl @Inject constructor(
    private val birthdayEntityDao: BirthdayEntityDao
) : BirthdayRepository {

    override suspend fun addBirthday(
        name: String,
        mobileNumber: String,
        birthdate: LocalDate,
        note: String
    ) = runCatching {

        val entity = BirthdayEntity(
            name = name,
            mobileNumber = mobileNumber,
            birthdate = birthdate,
            birthdayMonth = birthdate.monthValue,
            note = note,
        )

        birthdayEntityDao.addBirthday(entity)
    }

    override fun fetchAllContacts(): Flow<List<ContactCardInfo>> {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

        return birthdayEntityDao.fetchAllContacts().map { birthdayEntities ->
            birthdayEntities.map { it.toContactCardInfo(formatter = formatter) }
                .sortedBy { it.name }
        }
    }

    override fun fetchAllBirthdays(): Flow<List<BirthdayCardInfo>> {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

        return birthdayEntityDao.getAllContactsSortedByMonth().map { list ->
            list.map { birthdayEntity ->
                val sortingDate =
                    birthdayEntity.birthdate
                        .withYear(today.year)
                        .let { date ->
                            if (date.isBefore(today)) date.plusYears(1) else date
                        }

                BirthdayCardInfo(
                    contactId = birthdayEntity.contactId,
                    name = birthdayEntity.name,
                    date = birthdayEntity.birthdate.format(formatter),
                    dateUsedForSorting = sortingDate
                )
            }.sortedBy { it.dateUsedForSorting }
        }
    }

    override fun getListOfContacts(): List<UIBirthdayData> {
        return birthdayEntityDao.getAllContacts().map { birthdayEntity ->
            birthdayEntity.toUIBirthdayData()
        }
    }

    override suspend fun checkTodayBirthday(date: String): List<UIBirthdayData> {
        Log.d("today date", date)
        return birthdayEntityDao.checkTodayBirthday(date = date)
            .map { it.toUIBirthdayData() }
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
            id = id,
            name = name,
            mobileNumber = mobileNumber,
            birthdate = birthdate,
            note = note
        )
    }

    override suspend fun getPersonProfile(contactId: Int): UIBirthdayData {
        return birthdayEntityDao.getPersonProfile(id = contactId)
            .let { it.toUIBirthdayData() }
    }

    override suspend fun getUpcomingBirthdayToSchedule(): UIBirthdayData? {
        return birthdayEntityDao.getUpcomingBirthday()?.let { it.toUIBirthdayData() }
    }
}