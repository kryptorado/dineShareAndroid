package com.example.dineshareandroid.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.ChatDataTwo
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel: ViewModel() {
    val chatDataList = MutableLiveData<MutableList<ChatDataTwo>>()

    fun getChatData(chatRoomId: String) {
        viewModelScope.launch  {
            val chatData = withContext(Dispatchers.IO) {
                UserData.getChatData(chatRoomId)
            }
            chatDataList.value = chatData.toMutableList()
        }
    }

    fun createChatData(chatData: ChatDataTwo) {
        viewModelScope.launch  {
            val isSuccess = withContext(Dispatchers.IO) {
                UserData.createChatData(chatData)
            }
        }
    }
}