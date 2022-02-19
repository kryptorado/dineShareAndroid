package com.example.dineshareandroid.ui.matching

import android.util.Log
import androidx.lifecycle.*
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.backend.UserData.getProfile
import kotlinx.coroutines.launch
import okhttp3.*
import okio.IOException
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType

import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.json.JSONArray
import kotlin.collections.HashMap


class MatchingViewModel: ViewModel() {
    private val TAG = "MatchingViewModel"
    val lol = MutableLiveData<Response?>()
    val queueEnterSuccess = MutableLiveData<Boolean>()

    private val BACKEND_BASE_URL = "http://192.168.0.37:3000/matching/"

    private var user: User? = null
    val timer = Timer()



//    init {
//        viewModelScope.launch {
//            user = getProfile()
//        }
//    }



    fun enterQueue() {
        val jsonObject = JSONObject()
        val interest1 = JSONObject()
        interest1.put("1", 3)

        val interest2 = JSONObject()
        interest2.put("2", 4)

        val interests = JSONArray(listOf(interest1, interest2))


        var list = mutableMapOf<Any?, Any?>()
        list["1"] = 2
        list["2"] = 2
        list["3"] = 2
        list["4"] = 2
        list["5"] = 2


//        val interests = JSONArray(list)

        jsonObject.put("uId", "9999")
        jsonObject.put("interests", interests)


        val client = OkHttpClient()
        val url = "${BACKEND_BASE_URL}enterQueue"
        val mediaType = "application/json".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .post(body)
            .url(url)
            .build()


        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "enter queue success ${response?.body?.string()}")

//                val body = response?.body?.string()
//                println(body)
//                val gson = GsonBuilder().create()
//                val PlayerStats = gson.fromJson(body, PlayerStats::class.java)
//                println(PlayerStats)
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "enter queue fail $e")
            }
        })



    }

    private fun pollQueue() {
        val timer = Timer()
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


    fun call() {

        val url = "${BACKEND_BASE_URL}pollQueue/1234"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "request success ${response?.body?.string()}")

//                val body = response?.body?.string()
//                println(body)
//                val gson = GsonBuilder().create()
//                val PlayerStats = gson.fromJson(body, PlayerStats::class.java)
//                println(PlayerStats)
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "request FAIL $e")

                println("API execute failed")
            }
        })
    }
}