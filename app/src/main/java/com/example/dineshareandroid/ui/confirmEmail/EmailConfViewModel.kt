package com.example.dineshareandroid.ui.confirmEmail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User

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
        writeUserToDB()
    }

    private fun onLoginError(error: AuthException) {
        confirmSuccess.postValue(false)
        confirmFailedMessage.postValue(error.message)
        Log.e(TAG, "onLoginError: auth exception $error")
    }

    private fun writeUserToDB() {
        val interests = mutableListOf<Int>()
        interests.addAll(mutableListOf(1, 2, 3)) // TODO: don't hardcode all the interests

        val user = User.builder()
            .id(Amplify.Auth.currentUser.userId)
            .firstName(_firstName)
            .lastName(_lastName)
            .interests(interests)
            .email(_email)
            .build()

        Amplify.API.mutate(
            "dineshareandroid",
            ModelMutation.create(user),
            {
                confirmSuccess.postValue(true)
                Log.i(TAG, "CREATE user succeeded: $it")
            },
            {
                confirmFailedMessage.postValue(it.message)
                Log.e(TAG, "CREATE user failed", it)
            }
        )
    }


}