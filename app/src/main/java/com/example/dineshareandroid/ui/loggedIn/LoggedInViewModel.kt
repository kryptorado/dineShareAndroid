package com.example.dineshareandroid.ui.loggedIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.backend.UserData

class LoggedInViewModel: ViewModel() {
//    val user: LiveData<User?> = liveData {
//        val user = UserData.getDynamoUser()
//        emit(user)
//    }

    val rtmToken: LiveData<String?> = liveData {
        val rtmToken = UserData.getUserRtmToken()
        emit(rtmToken)
    }

}