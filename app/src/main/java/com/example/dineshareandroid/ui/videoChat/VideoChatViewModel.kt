package com.example.dineshareandroid.ui.videoChat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoChatViewModel: ViewModel() {
    val logCreateSuccess = MutableLiveData<Boolean>()
    val otherUserName = MutableLiveData<String>()
    private var startTime: Long = 0
    var callLength: Long = 0

    fun startCallTimer() {
        startTime = System.currentTimeMillis()
    }

    fun endCallTimer() {
        callLength = System.currentTimeMillis() - startTime
    }

    fun createCallLog(otherUserName: String) {
        val callLengthFormatted = getFormattedTime(callLength)

        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                UserData.getDynamoUser()
            }
            val isSuccess = withContext(Dispatchers.IO) {
                UserData.createCallLog(user, callLengthFormatted, otherUserName)
            }

            logCreateSuccess.value = isSuccess
        }
    }

    fun getRemoteUserName(remoteUserId: String) {
        viewModelScope.launch {
            val otherUser = withContext(Dispatchers.IO) {
                UserData.getDynamoUserById(remoteUserId)
            }
            otherUserName.value = otherUser?.firstName
        }
    }

    private fun getFormattedTime(callLength: Long): String {
        val hours = (callLength / (1000 * 60 * 60) % 24)
        val minutes = callLength / 1000 / 60
        val seconds = callLength / 1000 % 60

        return("$hours hours, $minutes minutes and $seconds seconds.")
    }

}