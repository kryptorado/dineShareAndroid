package com.example.dineshareandroid.ui.matching


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.confirmEmail.EmailConfirmationActivity
import com.example.dineshareandroid.ui.videoChat.VideoChatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_matching.*
import okhttp3.OkHttpClient


class MatchingFragment : Fragment(R.layout.fragment_matching) {
    private val TAG = "MatchingFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: MatchingViewModel by viewModels()

        model.interests.observe(this, { interests ->
            if (interests != null) {
                model.enterQueuev2(interests)
            }
            Log.d(TAG, "request success $interests")
        })


        model.matchResult.observe(this, {result ->
            Log.d(TAG, "received match results: ${result.channelName} ${result.otherUser} ${result.token}")

            val intent = Intent(activity, VideoChatActivity::class.java)

            intent.putExtra("channelName", result.channelName)
            intent.putExtra("token", result.token)

            startActivity(intent)

//            startActivity(Intent(activity, VideoChatActivity::class.java))

        })

        matching_poll_button.setOnClickListener {
            model.pollQueue()
        }

        matching_video_chat_button.setOnClickListener {
            startActivity(Intent(activity, VideoChatActivity::class.java))
        }


    }
}