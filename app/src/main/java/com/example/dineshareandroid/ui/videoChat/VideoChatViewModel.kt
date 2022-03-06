package com.example.dineshareandroid.ui.videoChat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class VideoChatViewModel: ViewModel() {
    val otherUserName = MutableLiveData<String>()
    private var startTime: Long = 0
    var callLength: Long = 0

    fun startCallTimer() {
        startTime = System.currentTimeMillis()
    }

    fun endCallTimer() {
        callLength = System.currentTimeMillis() - startTime
    }

    fun getCallLength(): String {
        return getFormattedTime(callLength)
    }

    fun getRemoteUserName(remoteUserId: String) {
        viewModelScope.launch {
            val otherUser = withContext(Dispatchers.IO) {
                UserData.getDynamoUserById(remoteUserId)
            }
            otherUserName.value = otherUser?.firstName?.capitalize(Locale.ROOT)
        }
    }

    private fun getFormattedTime(callLength: Long): String {
        val hours = (callLength / (1000 * 60 * 60) % 24)
        val minutes = callLength / 1000 / 60
        val seconds = callLength / 1000 % 60

        return("$hours hours, $minutes minutes and $seconds seconds.")
    }
}