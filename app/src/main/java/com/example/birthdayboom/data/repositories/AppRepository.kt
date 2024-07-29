package com.example.birthdayboom.data.repositories

import android.content.Intent

interface AppRepository {
    suspend fun exportData() : Intent?

    suspend fun importData()
}