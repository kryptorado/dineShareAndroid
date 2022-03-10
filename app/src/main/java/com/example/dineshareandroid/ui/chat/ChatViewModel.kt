package com.example.dineshareandroid.ui.chat

import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.CallLog
import com.amplifyframework.datastore.generated.model.ChatData
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel: ViewModel() {
    val chatDataList = MutableLiveData<MutableList<ChatData>>()
//    val chatDataList: LiveData<List<ChatData>> = liveData {
//        val user = UserData.getChatData()
//        emit(user)
//    }

    fun getChatData(chatRoomId: String) {
        viewModelScope.launch  {
            val chatData = withContext(Dispatchers.IO) {
                UserData.getChatData(chatRoomId)
            }
            chatDataList.value = chatData.toMutableList()
        }
    }

    fun createChatData(chatData: ChatData) {
        viewModelScope.launch  {
            val isSuccess = withContext(Dispatchers.IO) {
                UserData.createChatData(chatData)
            }
//            chatDataList.value = chatData
        }
    }

}