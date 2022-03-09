package com.example.dineshareandroid

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import io.agora.rtm.*
import kotlinx.coroutines.GlobalScope
import java.lang.Exception
import java.lang.RuntimeException

class AmplifyApp: Application() {
    private val TAG = "AmplifyApp"
    var mRtmClient: RtmClient? = null

    //    val applicationScope = GlobalScope

    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
            Log.i(TAG, "Initialized Amplify")

            createRTMClient()
            Log.i(TAG, "Created RTM Client")

            Amplify.Auth.fetchAuthSession(
                { Log.i(TAG, "Auth session = $it") },
               { Log.e(TAG, "Failed to fetch auth session", it) }
            )
        } catch (error: AmplifyException) {
            Log.e(TAG, "Could not initialize Amplify", error)
        }
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
}