package com.example.birthdayboom.data.repositories

import com.example.birthdayboom.data.database.dao.BirthdayEntityDao
import com.example.birthdayboom.data.database.entity.BirthdayEntity
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

    override fun fetchAllBirthdaysToday(): List<BirthdayEntity> {
        val today = LocalDate.now()

        return birthdayEntityDao.getAllContacts().filter { birthdayEntity ->
            birthdayEntity.birthdate.isEqual(today)
        }
    }

    override suspend fun setMockData(mockContacts: List<BirthdayEntity>): Result<Unit> = runCatching {
        if(birthdayEntityDao.getContactsCount() != 0) return@runCatching

//        birthdayEntityDao.deleteAllContacts()
        birthdayEntityDao.addBirthdaysList(birthdayList = mockContacts)
    }
}