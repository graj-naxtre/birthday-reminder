package com.example.birthdayboom.ui.screens.contact

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.ui.screens.contact.utils.DateUtils
import com.example.birthdayboom.utils.NotificationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    private val _allBirthdayContacts = MutableStateFlow<List<UIBirthdayData>>(emptyList())
    val allBirthdayContacts = _allBirthdayContacts.asStateFlow()

    private val lastSearchResult = MutableStateFlow<List<UIBirthdayData>>(emptyList())

    private val _personProfileDetails: MutableState<UIBirthdayData?> = mutableStateOf(null)
    val personProfileDetails by _personProfileDetails

    fun notifyUser(name: String) {
        viewModelScope.launch {
            notificationHelper.notifyTheUser(name, 0)
        }
    }

    // TODO: have to sort by names
    fun fetchAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val dateUtils = DateUtils()
            birthdayRepository.fetchAllContacts().collect {
                it.map { uiBirthdayData ->
                    uiBirthdayData.copy(
                        birthdateString = dateUtils.convertDate(uiBirthdayData.birthdateMillis)
                    )
                }.also { value ->
                    _allBirthdayContacts.value = value
                    lastSearchResult.value = value
                }
            }
        }
    }

    fun searchByContactName(searchText: String) {
        viewModelScope.launch {
            _allBirthdayContacts.value = _allBirthdayContacts.value.filter {
                it.name.startsWith(searchText)
            }
        }
    }

    fun showAllContacts() {
        viewModelScope.launch {
            if (_allBirthdayContacts.value.isNotEmpty()) {
                _allBirthdayContacts.value = lastSearchResult.value
            }
        }
    }

    fun fetchPersonProfile(contactId: Int) {
        viewModelScope.launch {
            _personProfileDetails.value = birthdayRepository.getPersonProfile(contactId)
        }
    }

    fun notifyUser(date: String, hour: String, minute: String) {

    }

//    private fun createWorkRequest(message: String, timeDelayInMinutes: Long) {
//        val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
//            .setInitialDelay(timeDelayInMinutes, TimeUnit.MINUTES)
//            .setInputData(
//                workDataOf(
//                    "title" to "Reminder",
//                    "message" to message,
//                )
//            )
//            .build()
//
//        WorkManager.getInstance().enqueue(myWorkRequest)
//    }
}


// flow lagado
// sorted name order me display
// intercept krke date modify krdo
