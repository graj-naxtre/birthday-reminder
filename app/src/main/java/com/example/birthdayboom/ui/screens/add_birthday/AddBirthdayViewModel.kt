package com.example.birthdayboom.ui.screens.add_birthday

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.ui.viewmodel.BaseViewModel
import com.example.birthdayboom.utility.helper.isValidPhoneNumber
import com.example.birthdayboom.utility.toast_holder.ToastHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddBirthdayViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository,
) : BaseViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    var name by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var dob by mutableStateOf("")
    var birthdayNote by mutableStateOf("")

    fun addContact(navController: NavController){
        viewModelScope.launch(ioDispatcher) {
            val isValidated = performValidation()
            _isLoading.update { true }

            if(!isValidated) return@launch

            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val birthDate = LocalDate.parse(dob, formatter)

            birthdayRepository.addBirthday(
                name = name,
                mobileNumber = phoneNumber,
                birthdate = birthDate,
                note = birthdayNote
            ).onSuccess {
                ToastHolder.displayToast(message = "Contact added successfully")
                withContext(mainDispatcher){
                    navController.navigateUp()
                }
            }.onFailure {
                ToastHolder.displayToast(message = it.localizedMessage ?: "Error occurred !!")
            }

            _isLoading.update { false }
        }
    }

    private fun performValidation() : Boolean {
        return if(name.isBlank()){
            ToastHolder.displayToast(message = "Name cannot be empty")
            false
        } else if(!phoneNumber.isValidPhoneNumber()){
            ToastHolder.displayToast(message = "Phone Number should be valid")
            false
        } else true
    }
}