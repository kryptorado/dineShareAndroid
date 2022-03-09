package com.example.dineshareandroid.ui.conversations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amplifyframework.datastore.generated.model.ChatRoom
import com.example.dineshareandroid.backend.UserData

class ConversationsViewModel: ViewModel() {
    val chatRooms: LiveData<List<ChatRoom>> = liveData {
        val chatRooms = UserData.getChatRooms()
        emit(chatRooms)
    }


}