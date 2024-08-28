package com.example.birthdayboom.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.birthdayboom.data.database.entity.BirthdayEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface BirthdayEntityDao {
    @RawQuery
    fun insertDataRawFormat(query: SupportSQLiteQuery): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBirthday(vararg birthday: BirthdayEntity)

    @Query("SELECT * FROM birthdays")
    fun fetchAllBirthdays(): Flow<List<BirthdayEntity>>

    @Query("SELECT * FROM birthdays WHERE SUBSTR(birthdate, 1, 5) = :date")
    suspend fun checkTodayBirthday(date: String): List<BirthdayEntity>

    @Query("UPDATE birthdays SET name = :name, birthdate = :birthdate, mobile_no = :mobileNumber, reminder_time = :reminderTime, note = :note WHERE contactId = :id")
    suspend fun updateBirthdayNote(
        id: Int,
        name: String,
        mobileNumber: String,
        birthdate: String,
        reminderTime: String,
        note: String
    )

    @Query("SELECT * FROM birthdays WHERE contactId = :id")
    suspend fun getPersonProfile(id: Int): BirthdayEntity

    @Query("SELECT * FROM birthdays ORDER BY birthdate ASC LIMIT 1")
    suspend fun getUpcomingBirthday() : BirthdayEntity?
}