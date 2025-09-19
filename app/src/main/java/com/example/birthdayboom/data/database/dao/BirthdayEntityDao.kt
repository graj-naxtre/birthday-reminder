package com.example.birthdayboom.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.birthdayboom.data.database.entity.BirthdayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayEntityDao {
    @RawQuery
    fun insertDataRawFormat(query: SupportSQLiteQuery): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBirthday(birthday: BirthdayEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBirthdaysList(birthdayList: List<BirthdayEntity>)

    @Query("SELECT * FROM birthdays")
    fun fetchAllContacts(): Flow<List<BirthdayEntity>>

    @Query("SELECT * FROM birthdays ORDER BY BIRTH_MONTH ASC")
    fun getAllContactsSortedByMonth(): Flow<List<BirthdayEntity>>

    @Query("SELECT * FROM birthdays")
    fun getAllContacts(): List<BirthdayEntity>

    @Query("UPDATE birthdays SET NAME = :name, BIRTHDATE = :birthdate, MOBILE_NUMBER = :mobileNumber, NOTE = :note WHERE contactId = :id")
    suspend fun updateBirthdayDetails(
        id: Int,
        name: String,
        mobileNumber: String,
        birthdate: String,
        note: String
    )

    @Query("SELECT COUNT(*) FROM birthdays")
    suspend fun getContactsCount() : Int

    @Query("DELETE FROM birthdays")
    suspend fun deleteAllContacts()
}