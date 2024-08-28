package com.example.birthdayboom.ui.screens.setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayboom.data.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    fun exportData(callback: (Intent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.exportToCSVFile().let { isExportComplete ->
                if (isExportComplete) {
                    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "text/csv"
                        putExtra(Intent.EXTRA_TITLE, "export_birthdate.csv")
                    }
                    callback(intent)
                }
            }
        }
    }

    fun moveData(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.moveCSVFileToExternal(uri)
        }
    }

    fun importData(callback: (Intent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "*/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            val chooserIntent = Intent.createChooser(intent, "Pick a CSV file with birthdays")
            callback(chooserIntent)
        }
    }

    @SuppressLint("Range")
    fun extractFile(uri: Uri, context: Context, callback: (File) -> Unit) {
        viewModelScope.launch {
            try {
                val fileType = context.contentResolver.getType(uri)
                val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
                val fileName = "temporary_file" + if(fileExtension != null) ".$fileExtension" else ""
                val tempFile = File(context.cacheDir, fileName)
                tempFile.createNewFile()

                val copiedFile = with(tempFile){
                    val outputStream = FileOutputStream(this)
                    val inputStream = context.contentResolver.openInputStream(uri)

                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                    this
                }
                copiedFile.let(callback)
            } catch (e: Exception){
                Log.e("SettingViewModel","message: ${e.localizedMessage}", e)
            }
        }
    }

    fun processFile(file: File) {
        viewModelScope.launch {
            appRepository.importData(file = file)
        }
    }
}