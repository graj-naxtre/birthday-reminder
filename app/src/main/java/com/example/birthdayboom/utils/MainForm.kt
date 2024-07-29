package com.example.birthdayboom.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.validators.MinLengthValidator
import ch.benlu.composeform.validators.NotEmptyValidator
import java.text.SimpleDateFormat
import java.util.Date

class MainForm : Form() {

    override fun self(): Form {
        return this
    }

    @FormField
    val name = FieldState(
        state = mutableStateOf<String?>(""),
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val birthdate = FieldState(
        state = mutableStateOf<Date?>(null),
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val mobileNumber = FieldState(
        state = mutableStateOf<String?>(""),
        validators = mutableListOf(MinLengthValidator(10,"must be 10 digits"))
    )

    @FormField
    val note = FieldState(state = mutableStateOf<String?>(""))

    @SuppressLint("SimpleDateFormat")
    fun dateToString(date:Date?): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.format(date?: Date())
    }
}