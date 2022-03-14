package com.example.dineshareandroid.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amplifyframework.auth.AuthException
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.backend.UserData


class ProfileViewModel: ViewModel() {
    private val TAG = "ProfileViewModel"
    val user: LiveData<User?> = liveData {
        val user = UserData.getDynamoUser()
        emit(user)
    }
    val logoutSuccess = MutableLiveData<Boolean>()

    fun logoutUser() {
        Amplify.Auth.signOut(
            this::onLogoutSuccess,
            this::onError
        )
    }

    private fun onError(error: AuthException) {
        logoutSuccess.postValue(false)
        Log.e(TAG, "logout error: $error")
    }

    private fun onLogoutSuccess() {
        logoutSuccess.postValue(true)
    }
}