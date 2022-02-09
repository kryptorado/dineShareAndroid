package com.example.dineshareandroid.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.backend.UserData

class MyViewModel : ViewModel() {
    private val TAG = "MyViewModel"

    private val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            loadUser()
        }
    }

    fun getUser(): LiveData<User> {
        return user
    }

    private fun loadUser() {
        Amplify.API.query(
            ModelQuery.get(User::class.java, Amplify.Auth.currentUser.userId),
            { dynamoUser ->
                val dynamoUserExists = dynamoUser.data != null
                if (dynamoUserExists) {
                    user.postValue(dynamoUser.data)

                    // check if first time signing in
//                    Log.i(TAG, "Query results = ${(dynamoUser.data as User)}")
                } else {
                    Amplify.Auth.fetchUserAttributes(
                        this::onFetchSuccess,
                        this::onFetchError
                    )
                }
            },
            { Log.e(TAG, "Query failed", it) }
        )
    }

    private fun onFetchSuccess(attrs: List<AuthUserAttribute>) {
        val attrMap = attrs.map { it.key to it.value }.toMap()
        val firstName = attrMap[AuthUserAttributeKey.givenName()]
        val lastName = attrMap[AuthUserAttributeKey.familyName()]
        val email = attrMap[AuthUserAttributeKey.email()]

//        UserData.createDynamoUser(firstName, lastName, email)
    }

//
//    fun getUser(): LiveData<User> {
//        return _userData
//    }
//
//    fun getUsers(): LiveData<List<User>> {
//        return users
//    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }


    private fun onFetchError(error: AuthException) {
        Log.e("MyViewModel", "auth exception $error")
    }
}