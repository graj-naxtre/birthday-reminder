package com.example.birthdayboom.ui.screens.add_birthday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayboom.data.repositories.BirthdayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBirthdayViewModel @Inject constructor(private val birthdayRepository: BirthdayRepository) :
    ViewModel() {

    fun addContact(
        name: String,
        mobileNumber: String,
        birthdate: String,
        reminderTime: String,
        note: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            birthdayRepository.addBirthday(
                name = name,
                mobileNumber = mobileNumber,
                birthdate = birthdate,
                reminderTime = reminderTime,
                note = note
            )
        }
    }
}