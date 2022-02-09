package com.example.dineshareandroid.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazonaws.AmazonServiceException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.backend.UserData

class LoginViewModel: ViewModel()  {
    private val TAG = "LoginViewModel"
    val loginSuccess = MutableLiveData<Boolean>()
    val isNewUser = MutableLiveData<Boolean>()
    val dynamoUserProfile = MutableLiveData<User>()

    val loginFailedMessage = MutableLiveData<String?>()

    fun login(email: String, password: String) {
        Amplify.Auth.signIn(
            email,
            password,
            this::onLoginSuccess,
            this::onLoginError
        )
    }

    // Social sign in check if first time log in
    fun isNewUser() {
        // query dynamoDB
        Amplify.API.query(
            ModelQuery.get(User::class.java, Amplify.Auth.currentUser.userId),
            { user ->
                val dynamoUserExists = user.data != null
                if (dynamoUserExists) {
                    isNewUser.postValue(false)
//                    dynamoUserProfile.postValue(user.data)

                    Log.i(TAG, "Query results = ${(user.data as User)}")
                } else {
                    // Create new user in Dynamo
                    Amplify.Auth.fetchUserAttributes(
                        this::createDynamoUser,
                        this::onFetchError
                    )
                }
            },
            { Log.e(TAG, "Query failed", it) }
        )
    }

    private fun createDynamoUser(attrs: List<AuthUserAttribute>) {
        val attrMap = attrs.map { it.key to it.value }.toMap()
        val firstName = attrMap[AuthUserAttributeKey.givenName()]
        val lastName = attrMap[AuthUserAttributeKey.familyName()]
        val email = attrMap[AuthUserAttributeKey.email()]

        val interests = mutableListOf<Int>()
        interests.addAll(mutableListOf(1, 2, 3)) // TODO: don't hardcode all the interests

        val user = User.builder()
            .id(Amplify.Auth.currentUser.userId)
            .firstName(firstName)
            .lastName(lastName)
            .interests(interests)
            .email(email)
            .build()

        Amplify.API.mutate(
            ModelMutation.create(user),
            {
                isNewUser.postValue(true)
//                dynamoUserProfile.postValue(user)
                Log.i(TAG, "CREATE user succeeded: $it")
            },
            { Log.e(TAG, "CREATE user failed", it) }
        )
    }

    private fun onFetchError(error: AuthException) {
        Log.e(TAG, "User fetch failed $error")
    }

    private fun onLoginError(error: AuthException) {
        loginSuccess.postValue(false)
        loginFailedMessage.postValue((error.cause as AmazonServiceException).errorMessage)
        Log.e(TAG, "Login failed $error")
    }

    private fun onLoginSuccess(result: AuthSignInResult) {
        loginSuccess.postValue(true)
        Log.d(TAG, "Login success $result")
    }

}