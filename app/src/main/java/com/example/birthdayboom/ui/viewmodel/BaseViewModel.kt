package com.example.birthdayboom.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("BaseViewModel", "coroutine exception", throwable)
    }
    protected val ioDispatcher = Dispatchers.IO + exceptionHandler
    protected val mainDispatcher = Dispatchers.Main + exceptionHandler
}