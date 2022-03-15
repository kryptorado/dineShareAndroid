package com.example.dineshareandroid.ui.conversations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.ChatRoom
import com.example.dineshareandroid.backend.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConversationsViewModel: ViewModel() {
    fun deleteChatRoom(chatRoom: ChatRoom) {
        viewModelScope.launch  {
            val isDeleted = withContext(Dispatchers.IO) {
                UserData.deleteChatRoom(chatRoom)
            }
        }
    }

    val chatRooms: LiveData<List<ChatRoom>> = liveData {
        val chatRooms = UserData.getChatRooms()
        emit(chatRooms)
    }


}