package com.example.birthdayboom.ui.screens.add_birthday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.utils.ViewState
import com.example.birthdayboom.workmanager.ReminderWorkManager
import com.example.birthdayboom.workmanager.WorkScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddBirthdayViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository,
    private val workScheduler: WorkScheduler
) : ViewModel() {

    private val _errorState = MutableStateFlow<ViewState>(ViewState.Idle)
    val errorState = _errorState.asStateFlow()

    fun addContact(
        name: String,
        mobileNumber: String,
        birthdateMillis: Long,
        reminderTime: String,
        note: String,
        executeNavigation: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _errorState.emit(ViewState.Loading)
            runCatching {
                birthdayRepository.addBirthday(
                    name = name,
                    mobileNumber = mobileNumber,
                    birthdate = birthdateMillis,
                    reminderTime = reminderTime,
                    note = note
                )
                workScheduler.scheduleBirthdayReminderWorker()
            }.onSuccess {
                withContext(Dispatchers.Main){
                    executeNavigation()
                }
                _errorState.emit(ViewState.Idle)
            }.onFailure {
                it.message?.let { msg ->
                    _errorState.emit(ViewState.Error(msg))
                } ?: _errorState.emit(ViewState.Error(it.localizedMessage))

                _errorState.emit(ViewState.Idle)
            }
        }
    }
}