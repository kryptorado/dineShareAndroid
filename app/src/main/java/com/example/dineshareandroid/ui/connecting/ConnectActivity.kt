package com.example.dineshareandroid.ui.connecting

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.loggedIn.LoggedInActivity
import com.example.dineshareandroid.ui.videoChat.VideoChatActivity

class ConnectActivity : AppCompatActivity() {
    private val TAG = "ConnectActivity"
    private val model: ConnectViewModel by viewModels()

    override fun onDestroy() {
        super.onDestroy()
        model.cleanUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        // Enter matching queue as soon as we're on this screen
        model.interests.observe(this, { interests ->
            if (interests != null) {
                model.enterQueue(interests)
            }
            else {
                // if interests cannot be fetched, the matching cannot happen
                Toast.makeText(this, "Couldn't fetch interests. Please try again later.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoggedInActivity::class.java))
            }
        })

        // Start polling for matches if enterqueue was successful
        model.queueEnterSuccess.observe(this, { isSuccess ->
            if (isSuccess) {
                // start polling (calls every x seconds)
                model.pollQueueScheduler()
            } else {
                cleanup()
            }
        })

        // If match was found, call exit queue and start the video chat activity
        model.matchResult.observe(this, {result ->
            model.exitQueue()
            model.queueExitSuccess.observe(this, {isSuccess ->
                if (isSuccess) {
                    Log.d(TAG, "received match results: ${result.channelName} ${result.otherUser} ${result.token}")

                    val intent = Intent(this, VideoChatActivity::class.java)
                    intent.putExtra("channelName", result.channelName)
                    intent.putExtra("token", result.token)
                    startActivity(intent)
                    finish()
                } else {
                    cleanup()
                    Log.e(TAG, "couldn't exit queue")
                    // maybe take them back to logged in activity?
                }
            })
        })

        model.notEnoughUsers.observe(this, { isTrue ->
            if (isTrue) {
                Toast.makeText(this, "There aren't many users on the server now. This could take a while...", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun cleanup() {
        // cleanup state
        // make toast with error
        // go back to profile fragment
        model.cleanUp()
        runOnUiThread {
            Toast.makeText(this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show()
        }
        val handler = Handler()
        handler.postDelayed(Runnable {
            startActivity(Intent(this, LoggedInActivity::class.java))
            finish()
        }, 3000)
    }
}