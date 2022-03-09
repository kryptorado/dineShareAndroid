package com.example.dineshareandroid.ui.postCall

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.*

class PostCallViewModel: ViewModel() {
    private val TAG = "PostCallViewModel"
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    val logCreateSuccess = MutableLiveData<Boolean>()

    fun createCallLog(remoteUserFirstName: String, remoteUserLastName:String, callLengthFormatted: String) {
        viewModelScope.launch {
            val user = withContext(defaultDispatcher) {
                UserData.getDynamoUser()
            }
//            val isSuccess = withContext(defaultDispatcher + NonCancellable) {
            val isSuccess = withContext(defaultDispatcher) {
                UserData.createCallLog(user, callLengthFormatted, "$remoteUserFirstName $remoteUserLastName")
            }
            Log.d(TAG, "callLog creation success: $isSuccess")
            logCreateSuccess.value = true
        }
    }

    fun createChatRoom(channelName: String, remoteUserFirstName: String, remoteUserLastName: String,  remoteUserId: String) {
        // get channel name sent by video activity
        viewModelScope.launch {
//            val isSuccess = withContext(defaultDispatcher + NonCancellable) {
            val isSuccess = withContext(defaultDispatcher) {
                UserData.createChatRoom(channelName, "$remoteUserFirstName $remoteUserLastName", remoteUserId)
            }
        }
    }

}