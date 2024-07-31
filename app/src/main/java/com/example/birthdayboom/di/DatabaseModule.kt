package com.example.birthdayboom.di

import android.app.Application
import com.example.birthdayboom.data.database.dao.BirthdayEntityDao
import com.example.birthdayboom.data.database.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesRoomDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)!!
    }

    @Provides
    @Singleton
    fun provideBirthdayEntityDao(db: AppDatabase): BirthdayEntityDao {
        return db.birthdayEntityDao()
    }
}