package com.example.dineshareandroid.ui.loggedIn

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.callLog.CallLogFragment
import com.example.dineshareandroid.ui.conversations.ConversationsFragment
import com.example.dineshareandroid.ui.matching.MatchingFragment
import com.example.dineshareandroid.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import io.agora.rtm.*
import java.lang.Exception
import java.lang.RuntimeException


class LoggedInActivity : AppCompatActivity() {
    private val TAG = "LoggedInActivity"

    // RTM client instance
    var mRtmClient: RtmClient? = null

    // TODO::: CHECK HERE IF THIS USER IS STILL IN MATCHING QUEUE AND IF SO CALL CLEANUP
    // this will prevent them from leaving the matching server in the bad state
    // they could've remained in the queue if they force quit the app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logged_in_base)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener(navListener)
        bottomNav.selectedItemId = R.id.nav_profile;

        createRTMClient()  // for instant messaging
    }

    private fun createRTMClient() {
        // Initialize the RTM client
        try {
            val AppID = this.getString(R.string.agora_app_id)
            // Initialize the RTM client
            mRtmClient = RtmClient.createInstance(
                this, AppID,
                object : RtmClientListener {
                    override fun onConnectionStateChanged(state: Int, reason: Int) {
                        val text = """
                           Connection state changed to ${state}Reason: $reason
                           
                           """.trimIndent()
                        Log.d(TAG, text)

//                        writeToMessageHistory(text)
                    }

                    override fun onImageMessageReceivedFromPeer(
                        rtmImageMessage: RtmImageMessage?,
                        s: String?
                    ) {
                    }

                    override fun onFileMessageReceivedFromPeer(
                        rtmFileMessage: RtmFileMessage?,
                        s: String?
                    ) {
                    }

                    override fun onMediaUploadingProgress(
                        rtmMediaOperationProgress: RtmMediaOperationProgress?,
                        l: Long
                    ) {
                    }

                    override fun onMediaDownloadingProgress(
                        rtmMediaOperationProgress: RtmMediaOperationProgress?,
                        l: Long
                    ) {
                    }

                    override fun onTokenExpired() {
                        // todo: call agora for new token and save to user
                    }
                    override fun onPeersOnlineStatusChanged(map: Map<String?, Int?>?) {}
                    override fun onMessageReceived(rtmMessage: RtmMessage, peerId: String) {
                        val text = """Message received from $peerId Message: ${rtmMessage.getText()}"""
                        Log.d(TAG, text)

//                        writeToMessageHistory(text)
                    }
                })
        } catch (e: Exception) {
            throw RuntimeException("RTM initialization failed!")
        }
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