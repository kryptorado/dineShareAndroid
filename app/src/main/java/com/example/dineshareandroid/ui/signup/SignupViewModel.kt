package com.example.dineshareandroid.ui.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify

class SignupViewModel: ViewModel() {
    private val TAG = "SignupViewModel"
    private val EMAIL_EXISTS_ERROR = "This email is already registered"

    val signupSuccess = MutableLiveData<Boolean>()
    val signupFailedMessage = MutableLiveData<String?>()

    fun signup(email: String, password: String, attributes: ArrayList<AuthUserAttribute>) {
        Amplify.Auth.signUp(
            email,
            password,
            AuthSignUpOptions.builder().userAttributes(attributes).build(),
            this::onSignupSuccess,
            this::onSignupError
        )
    }

    private fun onSignupError(error: AuthException) {
        if ((error.cause as UsernameExistsException).errorCode == "UsernameExistsException") {
            signupFailedMessage.postValue(EMAIL_EXISTS_ERROR)
        } else {
            signupFailedMessage.postValue(error.message)
        }

        signupSuccess.postValue(false)
        Log.e(TAG, "Signup error: $error")
    }

    private fun onSignupSuccess(result: AuthSignUpResult) {
        signupSuccess.postValue(true)
        Log.d(TAG, "Signup success: $result")
    }
}