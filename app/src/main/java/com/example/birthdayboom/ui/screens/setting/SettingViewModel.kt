package com.example.birthdayboom.ui.screens.setting

import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayboom.data.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val appRepository: AppRepository): ViewModel() {
    fun exportData(intent: (Intent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.exportData()?.let(intent)
        }
    }

    fun importData() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}