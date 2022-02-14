package com.example.dineshareandroid.ui.profile

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User


class ProfileViewModel: ViewModel() {
    private val TAG = "ProfileViewModel"

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
            "dineshareandroid",
            ModelQuery.get(User::class.java, Amplify.Auth.currentUser.userId),
            { user.postValue(it.data) },
            { Log.e(TAG, "Query failed", it) }
        )
//        Amplify.Auth.fetchUserAttributes(
//            this::onFetchSuccess,
//            this::onError
//        )
    }

    private fun onFetchSuccess(attrs: List<AuthUserAttribute>) {
        val attrMap = attrs.map { it.key to it.value }.toMap()
        val firstName = attrMap[AuthUserAttributeKey.givenName()]
        val lastName = attrMap[AuthUserAttributeKey.familyName()]
        val email = attrMap[AuthUserAttributeKey.email()]

        val lol = User.builder()
            .id(Amplify.Auth.currentUser.userId)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .build()

        user.postValue(lol)

        Log.d(TAG, "fetch success $attrs")

    }

    private fun onError(error: AuthException) {
//        runOnUiThread {
//            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
//        }
        Log.e(TAG, "fetch exception $error")
    }

}