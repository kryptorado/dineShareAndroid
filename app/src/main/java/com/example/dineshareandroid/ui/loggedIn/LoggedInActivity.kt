package com.example.dineshareandroid.ui.loggedIn

import android.os.Build
import android.os.Bundle
import android.service.carrier.CarrierMessagingService
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.AmplifyApp
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.callLog.CallLogFragment
import com.example.dineshareandroid.ui.conversations.ConversationsFragment
import com.example.dineshareandroid.ui.matching.MatchingFragment
import com.example.dineshareandroid.ui.postCall.PostCallViewModel
import com.example.dineshareandroid.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import io.agora.rtm.*
import java.lang.Exception
import java.lang.RuntimeException


class LoggedInActivity : AppCompatActivity() {
    private val TAG = "LoggedInActivity"
    private val model: LoggedInViewModel by viewModels()

    // TODO::: CHECK HERE IF THIS USER IS STILL IN MATCHING QUEUE AND IF SO CALL CLEANUP
    // this will prevent them from leaving the matching server in the bad state
    // they could've remained in the queue if they force quit the app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logged_in_base)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener(navListener)
        bottomNav.selectedItemId = R.id.nav_profile

        model.rtmToken.observe(this) { token ->
                Log.d(TAG, "this shoud only run once")
                Log.d(TAG, "userid: ${token!!}")
                rtmLogin(token)
        }
    }

    private fun rtmLogin(rtmToken: String) {
        val app: AmplifyApp = applicationContext as AmplifyApp
        val rtmClient = app.mRtmClient

        val uid = Amplify.Auth.currentUser.userId
        val token = rtmToken

        // Log in to the RTM system
        rtmClient?.login(token, uid, @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        object : CarrierMessagingService.ResultCallback<Void?>, io.agora.rtm.ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
//                runOnUiThread {
//                    val toast = Toast.makeText(applicationContext, "sucess logging in!", Toast.LENGTH_LONG)
//                    toast.show()
//                }
            }
            override fun onFailure(errorInfo: ErrorInfo) {
                val text: CharSequence =
                    "User: " + uid + " failed to log in to the RTM system!" + errorInfo.toString()
                val duration = Toast.LENGTH_SHORT
                Log.e(TAG, errorInfo.toString())
//                runOnUiThread {
//                    val toast = Toast.makeText(applicationContext, text, duration)
//                    toast.show()
//                }
            }

            override fun onReceiveResult(p0: Void) {
                TODO("Not yet implemented")
            }
        })
    }


    private val navListener =
        NavigationBarView.OnItemSelectedListener{ item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_profile -> selectedFragment = ProfileFragment()
                R.id.nav_messages -> selectedFragment = ConversationsFragment()
                R.id.nav_call_log -> selectedFragment = CallLogFragment()
                R.id.nav_matching -> selectedFragment = MatchingFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment
                ).commit()
            }
            true
        }
}