package com.example.dineshareandroid.backend

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object UserData {
    private val TAG = "UserData"

    suspend fun getProfile() : User? {
        return suspendCoroutine { continuation ->
            Amplify.API.query(
                "dineshareandroid",
                ModelQuery.get(User::class.java, Amplify.Auth.currentUser.userId),
                { user ->
                    continuation.resume(user.data)
                    Log.i(TAG, "Queried")
                    Log.i(TAG, "response.data: ${user.data}")
                },
                { error ->
                    Log.e("UserDataBackend", "Query failure:", error)
                    continuation.resume(null)
                }
            )
        }
    }


    fun createDynamoUser(attrs: List<AuthUserAttribute>) {
        val attrMap = attrs.map { it.key to it.value }.toMap()
        val firstName = attrMap[AuthUserAttributeKey.givenName()]
        val lastName = attrMap[AuthUserAttributeKey.familyName()]
        val email = attrMap[AuthUserAttributeKey.email()]
//

        val interests = mutableListOf<Int>()
        interests.addAll(mutableListOf(1, 2, 3)) // TODO: don't hardcode all the interests

        val user = User.builder()
            .id(Amplify.Auth.currentUser.userId)
            .firstName(firstName)
            .lastName(lastName)
//            .interests(interests)
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

    private fun onFetchError(error: AuthException) {
        Log.e(TAG, "auth exception $error")
    }
}