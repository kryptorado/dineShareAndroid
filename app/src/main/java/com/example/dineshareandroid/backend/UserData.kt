package com.example.dineshareandroid.backend

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Interest
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.utils.Constants.interestNames
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

    suspend fun createDynamoUser(firstName: String, lastName: String, email: String): Boolean {
        return suspendCoroutine { continuation ->
            val user = User.builder()
                .id(Amplify.Auth.currentUser.userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build()

            // Add default interests
            var index = 1
            for (name in interestNames) {
                val interest = Interest.builder()
                    .name(name)
                    .strength(1)
                    .interestId(index)
                    .users(user)
                    .build()

                Amplify.API.mutate(
                    "dineshareandroid",
                    ModelMutation.create(interest),
                    {
                        Log.i(TAG, "CREATE interest succeeded: $it")
                    },
                    { Log.e(TAG, "CREATE interest failed", it) }
                )
                index += 1
            }

            Amplify.API.mutate(
                "dineshareandroid",
                ModelMutation.create(user),
                {
                    continuation.resume(true)
                    Log.i(TAG, "CREATE user succeeded: $it")
                },
                {
                    continuation.resume(false)
                    Log.e(TAG, "CREATE user failed", it)
                }
            )
        }
    }

    suspend fun getListOfInterests(): List<Interest> {
        /*
        * Returns them in the format ...
        * */
        val interestList = ArrayList<Interest>()

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                "dineshareandroid",
                ModelQuery.list(Interest::class.java, Interest.USERS.eq(Amplify.Auth.currentUser.userId)),
                { interests ->
                    if (interests != null) {
                        for (interest in interests.data) {
                            interestList.add(interest)
                            Log.i(TAG, "Interests are: $interest")

                        }
                        continuation.resume(interestList)
                    }

                },
                { error ->
                    Log.e(TAG, "Interest fetch fail: ", error)
                    continuation.resume(interestList)
                }
            )
        }

    }

    suspend fun updateInterests(interests: MutableList<Interest>): Boolean {
        return suspendCoroutine { continuation ->

            for (interest in interests) {
                Amplify.API.mutate(
                    "dineshareandroid",
                    ModelMutation.update(interest),
                    {
                        Log.i(TAG, "CREATE user succeeded: $it")
                    },
                    {
                        continuation.resume(false)
                        Log.e(TAG, "CREATE user failed", it)
                    }
                )
            }
            continuation.resume(true)
        }
    }
}