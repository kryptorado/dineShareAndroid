package com.example.dineshareandroid.ui.confirmEmail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmailConfViewModel: ViewModel() {
    private val TAG = "EmailConfViewModel"
    val confirmSuccess = MutableLiveData<Boolean>()
    val confirmFailedMessage = MutableLiveData<String?>()

    fun confirmEmail(code: String) {

    }

}