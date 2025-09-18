package com.example.birthdayboom.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.birthdayboom.data.database.converters.DateConverters
import com.example.birthdayboom.data.database.dao.BirthdayEntityDao
import com.example.birthdayboom.data.database.entity.BirthdayEntity

@Database(entities = [BirthdayEntity::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun birthdayEntityDao(): BirthdayEntityDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "birthday.db"
                    ).fallbackToDestructiveMigration(true)
                        .build()
                }
            }
            return INSTANCE
        }
    }
}