package com.example.birthdayboom.di

import android.content.Context
import com.example.birthdayboom.workmanager.WorkScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilityModule {
    @Provides
    @Singleton
    fun providesWorkScheduler(
        @ApplicationContext context: Context,
    ): WorkScheduler {
        return WorkScheduler(context)
    }
}