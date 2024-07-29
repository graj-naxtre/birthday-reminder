package com.example.birthdayboom.data.repositories

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import com.example.birthdayboom.data.database.dao.BirthdayEntityDao
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val context: Context,
    private val birthdayEntityDao: BirthdayEntityDao
) : AppRepository {

    override suspend fun exportData() : Intent? {
        return withContext(Dispatchers.IO) {
            try {
                writeDataToFile()?.let {file ->
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file
                    )

                    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "application/json"
                        putExtra(Intent.EXTRA_TITLE, "export_birthdate.json")
                        putExtra(Intent.EXTRA_STREAM, uri)
                    }

                    intent
                }
            } catch (e: Exception) {
                Log.e("AppRepositoryImpl", "exportData: ${e.localizedMessage}")
                null
            }
        }
    }

    private suspend fun writeDataToFile(): File? {
        return withContext(Dispatchers.IO) {
            try {
                val data = birthdayEntityDao.fetchAllBirthdays().first()
                val jsonData = Gson().toJson(data)
                val file = File(context.cacheDir, "export_birthdates.json")
                file.writeText(text = jsonData, charset = Charsets.UTF_8)
                file
            } catch (e: Exception) {
                Log.e("AppRepositoryImpl", "writeDataToFile: ${e.localizedMessage}")
                null
            }
        }
    }

    override suspend fun importData() {
        TODO("Not yet implemented")
    }
}