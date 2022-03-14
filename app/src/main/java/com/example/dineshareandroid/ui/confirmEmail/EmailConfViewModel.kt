package com.example.dineshareandroid.ui.confirmEmail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify
import com.example.dineshareandroid.backend.UserData
import com.example.dineshareandroid.backend.UserData.createDynamoUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmailConfViewModel: ViewModel() {
    private val TAG = "EmailConfViewModel"
    val confirmSuccess = MutableLiveData<Boolean>()
    val confirmFailedMessage = MutableLiveData<String?>()
    private lateinit var _email: String
    private lateinit var _password: String
    private lateinit var _firstName: String
    private lateinit var _lastName: String

    fun confirmEmail(code: String, email: String, password: String, firstName: String, lastName: String) {
        _email = email
        _password = password
        _firstName = firstName
        _lastName = lastName

        Amplify.Auth.confirmSignUp(
            email,
            code,
            this::onConfrimSuccess,
            this::onConfirmError
        )
    }

    private fun onConfrimSuccess(result: AuthSignUpResult) {
        relogin()
        Log.d(TAG, "onConfrimSuccess: confirm result $result")
    }

    private fun onConfirmError(error: AuthException) {
        confirmSuccess.postValue(false)
        confirmFailedMessage.postValue(error.message)
        Log.e(TAG, "onConfirmError: confirm exception $error")
    }

    private fun relogin() {
        Amplify.Auth.signIn(
            _email,
            _password,
            this::onLoginSuccess,
            this::onLoginError
        )
    }

    private fun onLoginSuccess(result: AuthSignInResult) {
        viewModelScope.launch {
//            val rtmToken = withContext(Dispatchers.IO) {
//                UserData.getUserRtmToken()
//            }
            val isCreated = withContext(Dispatchers.IO) {
                createDynamoUser(_firstName, _lastName, _email)
            }
            confirmSuccess.value = isCreated
        }
    }

    private fun onLoginError(error: AuthException) {
        confirmSuccess.postValue(false)
        confirmFailedMessage.postValue(error.message)
        Log.e(TAG, "onLoginError: auth exception $error")
    }
}