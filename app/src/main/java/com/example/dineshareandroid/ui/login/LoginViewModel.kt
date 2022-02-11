package com.example.dineshareandroid.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.cognitoidentityprovider.model.InvalidPasswordException
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException
import com.amazonaws.services.cognitoidentityprovider.model.UserNotFoundException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User


class LoginViewModel: ViewModel()  {
    private val TAG = "LoginViewModel"
    val loginSuccess = MutableLiveData<Boolean>()
    val loginFailedMessage = MutableLiveData<String?>()
    val isNewUser = MutableLiveData<Boolean>()
    val isUniqueEmail = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {
        Amplify.Auth.signIn(
            email,
            password,
            this::onLoginSuccess,
            this::onLoginError
        )
    }

    fun ssoLogin() {
        Amplify.Auth.fetchUserAttributes(
            this::checkSSOFirstTimeLogin,
            this::onFetchError
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
            "dineshareandroid",
            ModelMutation.create(user),
            {
                Log.i(TAG, "CREATE user succeeded: $it")
            },
            { Log.e(TAG, "CREATE user failed", it) }
        )
    }

    private fun checkSSOFirstTimeLogin (attrs: List<AuthUserAttribute>) {
        val attrMap = attrs.map { it.key to it.value }.toMap()
        val email = attrMap[AuthUserAttributeKey.email()]

        // Check if dynamo user already exists
        Amplify.API.query(
            "dineshareandroid",
            ModelQuery.get(User::class.java, Amplify.Auth.currentUser.userId),
            { user ->
                val dynamoUserExists = user.data != null
                if (dynamoUserExists) {

                    // Proceed with login since this user already exists
                    isNewUser.postValue(false)
                    Log.i(TAG, "Query results = ${(user.data as User)}")
                } else {
                    isNewUser.postValue(true)

                    // check if email is unique and if yes, create a new user
                    Amplify.API.query(
                        "dineshareandroidPublic",
                        ModelQuery.list(User::class.java, User.EMAIL.eq(email)),
                        { response ->
                            if (response.data != null) {
                                var isUnique = true
                                for(userData in response.data){
                                    if (userData.email.equals(email)) {
                                        isUnique = false
                                        isUniqueEmail.postValue(false)
                                    }
                                }
                                if (isUnique) {
                                    // Otherwise the email is unique
                                    isUniqueEmail.postValue(true)

                                    // Create new user
                                    createDynamoUser(attrs)
                                }
                                Log.i(TAG, "getDynamoUser = ${(response.data)}")
                            } else {
                                Log.i(TAG, "getDynamoUser = response.data is null")
                            }
                        },
                        { Log.e(TAG, "Query failed", it) }
                    )
                }
            },
            { Log.e(TAG, "Query failed", it) }
        )
    }


    private fun onFetchError(error: AuthException) {
        Log.e(TAG, "User fetch failed $error")
    }

    private fun onLoginError(error: AuthException) {
        loginSuccess.postValue(false)

        when (error) {
            is InvalidPasswordException -> Log.e("Demo", "Invalid password", error)
            is UserNotFoundException -> Log.e("Demo", "User not found", error)
            is UserNotConfirmedException -> Log.e("Demo", "User not confirmed", error)
            else -> {
                // Handle additional AuthException subtypes
                Log.w("Demo", "Unhandled error", error)
            }
        }

        // TODO: use above when block to construct error message
        loginFailedMessage.postValue((error.cause as AmazonServiceException).errorMessage)
        Log.e(TAG, "Login failed $error")
    }

    private fun onLoginSuccess(result: AuthSignInResult) {
        loginSuccess.postValue(true)
        Log.d(TAG, "Login success $result")
    }

}