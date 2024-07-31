package com.example.birthdayboom.ui.screens.setting

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayboom.data.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun importData() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}