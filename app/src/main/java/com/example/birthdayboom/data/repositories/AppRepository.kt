package com.example.birthdayboom.data.repositories

import android.net.Uri
import java.io.File

interface AppRepository {

    suspend fun importData(file: File)

    suspend fun exportToCSVFile(): Boolean

    suspend fun moveCSVFileToExternal(uri: Uri)
}