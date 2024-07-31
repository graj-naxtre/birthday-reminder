package com.example.birthdayboom.data.repositories

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.birthdayboom.data.database.dao.BirthdayEntityDao
import com.example.birthdayboom.data.database.db.AppDatabase
import com.example.birthdayboom.utils.CSVWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import javax.inject.Inject


class AppRepositoryImpl @Inject constructor(
    private val context: Context,
    private val birthdayEntityDao: BirthdayEntityDao,
    private val db: AppDatabase
) : AppRepository {

    override suspend fun exportToCSVFile(): Boolean {
        // scoped storage of our app
        val exportDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if (exportDir != null && !exportDir.exists()) { // create directory if doesn't exist
            exportDir.mkdirs()
        }

        val file = File(exportDir, "export_contacts.csv") // create a file instance
        try {
            return withContext(Dispatchers.IO) {
                file.createNewFile() // create a file
                val csvWrite = CSVWriter(FileWriter(file))
                val curCSV: Cursor = db.query("SELECT * FROM birthdays", null)
                csvWrite.writeNext(curCSV.columnNames)
                while (curCSV.moveToNext()) {
                    //Which column you want to export
                    val arrStr = arrayOfNulls<String>(curCSV.columnCount)
                    for (i in 0 until curCSV.columnCount - 1) arrStr[i] = curCSV.getString(i)
                    csvWrite.writeNext(arrStr)
                }
                csvWrite.close()
                curCSV.close()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "file created locally", Toast.LENGTH_SHORT).show()
                }
                true
            }
        } catch (sqlEx: java.lang.Exception) {
            Log.e("MainActivity", sqlEx.message, sqlEx)
            return false
        }
    }

    override suspend fun moveCSVFileToExternal(uri: Uri) {
        val exportDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(exportDir, "export_contacts.csv")
        if (file.exists()) {
            withContext(Dispatchers.IO) {
                try {
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        file.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                        file.delete()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "file moved to selected Location",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MyViewModel", "Error moving file", e)
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "file not found locally", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override suspend fun importData() {
        TODO("Not yet implemented")
    }
}