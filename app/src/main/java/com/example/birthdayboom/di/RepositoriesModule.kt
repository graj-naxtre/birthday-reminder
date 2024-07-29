package com.example.birthdayboom.di

import android.content.Context
import com.example.birthdayboom.data.database.dao.BirthdayEntityDao
import com.example.birthdayboom.data.database.mappers.BirthdayBiMapper
import com.example.birthdayboom.data.repositories.AppRepository
import com.example.birthdayboom.data.repositories.AppRepositoryImpl
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.data.repositories.BirthdayRepositoryImpl
import com.example.birthdayboom.utils.NotificationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun provideBiBirthdayMapper(): BirthdayBiMapper {
        return BirthdayBiMapper()
    }

    @Provides
    @Singleton
    fun providesBirthdayRepository(
        birthdayEntityDao: BirthdayEntityDao,
        birthdayBiMapper: BirthdayBiMapper
    ) : BirthdayRepository = BirthdayRepositoryImpl(birthdayEntityDao, birthdayBiMapper)

    @Provides
    @Singleton
    fun providesAppRepository(
        @ApplicationContext context: Context,
        birthdayEntityDao: BirthdayEntityDao
    ) : AppRepository = AppRepositoryImpl(context = context, birthdayEntityDao = birthdayEntityDao)

    @Provides
    @Singleton
    fun providesNotificationHelper(@ApplicationContext context: Context): NotificationHelper {
        return NotificationHelper(context)
    }
}