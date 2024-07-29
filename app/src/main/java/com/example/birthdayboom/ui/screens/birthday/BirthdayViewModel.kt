package com.example.birthdayboom.ui.screens.birthday

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayboom.data.database.models.GroupedUIBirthdayData
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.utils.CommunicationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    private val communicationUtil: CommunicationUtil,
    private val birthdayRepository: BirthdayRepository
) :
    ViewModel() {
    private val _allBirthdays = MutableStateFlow<List<GroupedUIBirthdayData>>(emptyList())
    val allBirthdays = _allBirthdays.asStateFlow()

    private val _mappedBirthdays = mutableMapOf<String, List<UIBirthdayData>>()
    val mappedBirthdays = _mappedBirthdays

    val isTodayBirthday = mutableStateOf(false)
    private val _todayBirthdayDetails: MutableState<List<UIBirthdayData>> =
        mutableStateOf(emptyList())
    val todayBirthdayDetails: State<List<UIBirthdayData>> = _todayBirthdayDetails

    // TODO: have to sort by months
    fun fetchAllBirthdays() {
        viewModelScope.launch(Dispatchers.IO) {
            birthdayRepository.fetchAllBirthdays().collect {
                _allBirthdays.value = it
            }
        }
    }

    fun isTodayBirthday(date: String) {
        val excludeYear: String = date.split("-").let { return@let "${it[0]}-${it[1]}" }
        viewModelScope.launch(Dispatchers.IO) {
            _todayBirthdayDetails.value = birthdayRepository.checkTodayBirthday(excludeYear)
                .let { isTodayBirthday.value = it.isNotEmpty(); return@let it }
            Log.d("today birthday", "${_todayBirthdayDetails.value}")
        }
    }

    fun updateBirthdayNote(
        id: Int, name: String, mobileNumber: String, birthdate: String,
        reminderTime: String, note: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            birthdayRepository.updateBirthdayNote(
                id = id, name = name,
                mobileNumber = mobileNumber,
                birthdate = birthdate,
                reminderTime = reminderTime,
                note = note
            )
        }
    }

    fun getBirthdayNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            birthdayRepository.getPersonProfile(contactId = id)
        }
    }

    fun makePhoneCall(phoneNumber: String) {
        communicationUtil.makePhoneCall(phoneNumber)
    }

    fun sendTextMessage(phoneNumber: String) {
        communicationUtil.sendTextMessage(message = "Happy Birthday", phoneNumber)
    }

    fun sendWhatsappMessage(message: String, phoneNumber: String){
        communicationUtil.sendWhatsappMessage(message, phoneNumber)
    }
}

