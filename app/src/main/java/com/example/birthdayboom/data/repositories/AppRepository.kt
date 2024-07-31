package com.example.birthdayboom.data.repositories

import android.net.Uri

interface AppRepository {

    suspend fun importData()

    suspend fun exportToCSVFile(): Boolean

    suspend fun moveCSVFileToExternal(uri: Uri)
}