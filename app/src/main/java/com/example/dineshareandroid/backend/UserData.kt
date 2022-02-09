package com.example.dineshareandroid.backend

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.InterestsActivity
import kotlinx.android.synthetic.main.activity_interests.*

object UserData {
    private val TAG = "UserData"

//    lateinit var currentUser: UserData
    private val _userData = MutableLiveData<User>()
    var firstName = ""


    fun getUserProfile () {
        Amplify.API.query(
            ModelQuery.get(User::class.java, Amplify.Auth.currentUser.userId),
            { user ->
                val dynamoUserExists = user.data != null
                if (dynamoUserExists) {

                    // check if first time signing in
                    Log.i(TAG, "Query results = ${(user.data as User)}")
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

    /*
    * Populate dynamoDB user table with data from Cognito
    * */
    private fun onFetchSuccess(attrs: List<AuthUserAttribute>) {
        val attrMap = attrs.map { it.key to it.value }.toMap()
        val firstName = attrMap[AuthUserAttributeKey.givenName()]
        val lastName = attrMap[AuthUserAttributeKey.familyName()]
        val email = attrMap[AuthUserAttributeKey.email()]

        createDynamoUser(firstName, lastName, email)
    }

    private fun createDynamoUser(firstName: String?, lastName: String?, email:String?): User {
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
            { Log.i(TAG, "CREATE user succeeded: $it") },
            { Log.e(TAG, "CREATE user failed", it) }
        )

        return user
    }

    private fun onFetchError(error: AuthException) {
        Log.e(TAG, "auth exception $error")
    }
}