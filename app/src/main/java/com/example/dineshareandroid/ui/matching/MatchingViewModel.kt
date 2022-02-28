package com.example.dineshareandroid.ui.matching

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Interest
import com.beust.klaxon.Klaxon
import com.example.dineshareandroid.backend.UserData
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class MatchingViewModel: ViewModel() {
    private val TAG = "MatchingViewModel"
    val matchResult = MutableLiveData<MATCHING_RESULT>()
    val queueEnterSuccess = MutableLiveData<Boolean>()

    private val BACKEND_BASE_URL = "http://192.168.0.37:3000/matching"

    val timer = Timer()

    val interests: LiveData<List<Interest>> = liveData {
        val interests = UserData.getListOfInterests()
        emit(interests)
    }


    fun enterQueuev2(interests: List<Interest>) {
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


        val client = OkHttpClient()
        val url = "${BACKEND_BASE_URL}/enterQueue"
        val mediaType = "application/json".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .post(body)
            .url(url)
            .build()


        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "enter queue success ${response?.body?.string()}")

            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "enter queue fail $e")
            }
        })
    }

    fun pollQueue() {
//        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                try {
                    call()
//                    SendToServer().execute()
                } catch (e: Exception) {
                    // TODO: handle exception
                }
            }
        }, 0, 2000)
    }


    data class MATCHING_RESULT(
        val state: String,
        val channelName: String = "",
        val token: String="",
        val otherUser: String=""
    )

    fun call() {
        val url = "${BACKEND_BASE_URL}/pollQueue/${Amplify.Auth.currentUser.userId}"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
//                    val body = response.body?.string()
                    val body = Klaxon().parse<MATCHING_RESULT>(response.body!!.string())

                    if (body?.state.equals("FOUND_MATCH")) {
                        timer.cancel() // stop the polling          // TODO: make sure to not cancel it twice
                        matchResult.postValue(body!!)
                    }

                    Log.d(TAG, "responseBody[0]:  ${body}")
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "request FAIL $e")

                println("API execute failed")
            }
        })
    }
}