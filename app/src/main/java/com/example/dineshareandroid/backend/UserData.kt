package com.example.dineshareandroid.backend

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.CallLog
import com.amplifyframework.datastore.generated.model.ChatRoom
import com.amplifyframework.datastore.generated.model.Interest
import com.amplifyframework.datastore.generated.model.User
import com.beust.klaxon.Klaxon
import com.example.dineshareandroid.ui.connecting.ConnectViewModel
import com.example.dineshareandroid.utils.Constants.interestNames
import okhttp3.*
import okio.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object UserData {
    private val TAG = "UserData"

    suspend fun getDynamoUser() : User? {
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


    suspend fun getDynamoUserById(userId: String): User? {
        return suspendCoroutine { continuation ->
            Amplify.API.query(
                "dineshareandroidPublic",
                ModelQuery.get(User::class.java, userId),
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

    suspend fun createDynamoUser(firstName: String, lastName: String, email: String, rtmToken: String): Boolean {
        return suspendCoroutine { continuation ->
            val user = User.builder()
                .id(Amplify.Auth.currentUser.userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .rtmToken(rtmToken)
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

    suspend fun deleteCallLog(callLog: CallLog) : Boolean {
        return suspendCoroutine { continuation ->
            Amplify.API.mutate(
                "dineshareandroid",
                ModelMutation.delete(callLog), //callLog id is 2nd paramter
                { user ->
                    continuation.resume(true)
                    Log.i(TAG, "Queried")
                    Log.i(TAG, "response.data: ${user.data}")
                },
                { error ->
                    Log.e("UserDataBackend", "Query failure:", error)
                    continuation.resume(false)
                }
            )
        }
    }

    suspend fun updateCallLog(callLogS: MutableList<CallLog>): Boolean {
        return suspendCoroutine { continuation ->

            for (callLog in callLogS) {
                Amplify.API.mutate(
                    "dineshareandroid",
                    ModelMutation.update(callLog),
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

    suspend fun getListOfInterests(): List<Interest> {
        val interestList = ArrayList<Interest>()

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                "dineshareandroid",
                ModelQuery.list(Interest::class.java, Interest.USERS.eq(Amplify.Auth.currentUser.userId)),
                { interests ->
                    for (interest in interests.data) {
                        interestList.add(interest)
                        Log.i(TAG, "Interests are: $interest")

                    }
                    continuation.resume(interestList)

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

    suspend fun getChatRooms(): List<ChatRoom> {
        val chatRoomList = ArrayList<ChatRoom>()

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                "dineshareandroid",
                ModelQuery.list(ChatRoom::class.java, ChatRoom.USER_ID.eq(Amplify.Auth.currentUser.userId)),
                { chatRooms ->
                    for (chatRoom in chatRooms.data) {
                        chatRoomList.add(chatRoom)
                        Log.i(TAG, "chatRooms are: $chatRooms")

                    }
                    continuation.resume(chatRoomList)

                },
                { error ->
                    Log.e(TAG, "chatRooms fetch fail: ", error)
                    continuation.resume(chatRoomList)
                }
            )
        }
    }

    suspend fun createCallLog(user: User?, callLength: String, calleeName: String): Boolean {
        return suspendCoroutine { continuation ->

            val callLog = CallLog.builder()
                .duration(callLength)
                .calleeName(calleeName)
                .users(user)
                .build()

            Amplify.API.mutate(
                "dineshareandroid",
                ModelMutation.create(callLog),
                {
                    continuation.resume(true)
                    Log.i(TAG, "CREATE call log succeeded: $it")
                },
                {
                    continuation.resume(false)
                    Log.e(TAG, "CREATE call log failed", it)
                })
        }
    }

    suspend fun getCallLog(): List<CallLog> {
        val callLogList = ArrayList<CallLog>()

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                "dineshareandroid",
                ModelQuery.list(CallLog::class.java, CallLog.USERS.eq(Amplify.Auth.currentUser.userId)),
                { callLogs ->
                    for (callLog in callLogs.data) {
                        callLogList.add(callLog)
                        Log.i(TAG, "callLog: $callLog")
                    }
                    continuation.resume(callLogList)
                },
                { error ->
                    Log.e(TAG, "chatRooms fetch fail: ", error)
                    continuation.resume(callLogList)
                }
            )
        }
    }

    suspend fun getUserRtmToken(): String? {
        return suspendCoroutine { continuation ->

            val url = "https://calm-castle-22371.herokuapp.com/rtm/${Amplify.Auth.currentUser.username}"
            val request = Request.Builder().url(url).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code == 200) {
                        val body = Klaxon().parse<RTM_TOKEN>(response.body!!.string())
                        continuation.resume(body?.rtmToken)
                    } else {
                        continuation.resume(null)
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resume(null)
                    Log.e(TAG, "RTM Token fetch FAIL $e")
                }
            })
        }
    }

    suspend fun createChatRoom(channelName: String, remoteUserName: String, remoteUserId: String): Boolean {
        return suspendCoroutine { continuation ->
            val chatRoom = ChatRoom.builder()
                .otherUserId(remoteUserId)
                .otherUserName(remoteUserName)
                .chatRoomId(channelName)
                .userId(Amplify.Auth.currentUser.userId)
                .userChatRoomsId(Amplify.Auth.currentUser.userId)
                .build()

            Amplify.API.mutate(
                "dineshareandroid",
                ModelMutation.create(chatRoom),
                {
                    continuation.resume(true)
                    Log.i(TAG, "CREATE chatroom succeeded: $it")
                },
                {
                    continuation.resume(false)
                    Log.e(TAG, "CREATE chatroom failed", it)
                })
        }
    }
}