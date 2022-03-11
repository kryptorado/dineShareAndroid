package com.example.dineshareandroid.ui.connecting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Interest
import com.beust.klaxon.Klaxon
import com.example.dineshareandroid.backend.MATCHING_RESULT
import com.example.dineshareandroid.backend.UserData
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ConnectViewModel: ViewModel() {
    private val TAG = "ConnectViewModel"
    private val BACKEND_BASE_URL = "http://192.168.0.47:3000/matching" // TODO: replace with deployed URL
    private val ENTER_QUEUE_URL = "${BACKEND_BASE_URL}/enterQueue"
    private val POLL_QUEUE_URL = "${BACKEND_BASE_URL}/pollQueue/${Amplify.Auth.currentUser.userId}"
    private val EXIT_QUEUE_URL = "${BACKEND_BASE_URL}/doneCall/${Amplify.Auth.currentUser.userId}"
    private val CLEANUP_URL = "${BACKEND_BASE_URL}/cleanup/${Amplify.Auth.currentUser.userId}"

    val matchResult = MutableLiveData<MATCHING_RESULT>()
    val queueEnterSuccess = MutableLiveData<Boolean>()
    val queueExitSuccess = MutableLiveData<Boolean>()
    val notEnoughUsers = MutableLiveData<Boolean>()
    private var notEnoughUsersCounter = 1

    val pollQueueTimer = Timer()
    private var isTimerRunning = false


    // Runs as soon as ConnectActivity is created
    val interests: LiveData<List<Interest>> = liveData {
        val interests = UserData.getListOfInterests()
        emit(interests)
    }


    fun enterQueue(interests: List<Interest>) {
        val jsonObject = getJsonInterests(interests)

        val client = OkHttpClient()
        val url = ENTER_QUEUE_URL
        val mediaType = "application/json".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .post(body)
            .url(url)
            .build()


        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 201) {
                    queueEnterSuccess.postValue(true)
                    Log.d(TAG, "enter queue success ${response?.body?.string()}")
                } else {
                    queueEnterSuccess.postValue(false)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                queueEnterSuccess.postValue(false)
                Log.d(TAG, "enter queue fail $e")
            }
        })
    }

    private fun getJsonInterests(interests: List<Interest>): JSONObject {
        val interestsObjList = mutableListOf<Any>()

        for (interest in interests) {
            val interestObj = JSONObject()
            interestObj.put(interest.interestId.toString(), interest.strength)
            interestsObjList.add(interestObj)
        }

        val interestsObjArr = JSONArray(interestsObjList)
        val jsonObject = JSONObject()

        jsonObject.put("uId", Amplify.Auth.currentUser.userId)
        jsonObject.put("interests", interestsObjArr)

        return jsonObject
    }

    fun pollQueueScheduler() {
        pollQueueTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                try {
                    isTimerRunning = true
                    pollQueue()
                } catch (e: Exception) {
                    Log.e(TAG, "Something went wrong with pollQueueScheduler: $e")
                    // TODO: handle exception
                }
            }
        }, 1000, 4000)
    }

    private fun cancelPollQueueTimer() {
        if (isTimerRunning) {
            pollQueueTimer.cancel()
        }
    }

    fun cleanUp() {
        cancelPollQueueTimer()
        val url = CLEANUP_URL
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    Log.d(TAG, "cleanup success")
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "cleanup FAIL $e")
            }
        })
    }

    fun exitQueue() {
        val url = EXIT_QUEUE_URL
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 204) {
                    queueExitSuccess.postValue(true)
                } else {
                    queueExitSuccess.postValue(false)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                queueExitSuccess.postValue(false)
                Log.e(TAG, "ExitQueue FAIL $e")
            }
        })
    }


    fun pollQueue() {
        val url = POLL_QUEUE_URL
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
//        Log.e(TAG, ".......................................................................")

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val body = Klaxon().parse<MATCHING_RESULT>(response.body!!.string())

                    if (body?.state.equals("FOUND_MATCH")) {
                        cancelPollQueueTimer()
                        matchResult.postValue(body!!)
                    }
                    if (body?.state.equals("NOT_ENOUGH_USERS")) {
                        notEnoughUsersCounter += 1
                    }

                    // every few seconds let the user know that there aren't many users now to get matched
                    if (notEnoughUsersCounter % 5 == 0) {
                        notEnoughUsers.postValue(true)
                    }

                } else {
                    // TODO: handle this error
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "pollQueue FAIL $e")
            }
        })
    }
}