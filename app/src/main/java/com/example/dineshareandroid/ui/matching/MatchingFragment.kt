package com.example.dineshareandroid.ui.matching


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.videoChat.VideoChatActivity
import kotlinx.android.synthetic.main.fragment_matching.*
import okhttp3.OkHttpClient


class MatchingFragment : Fragment(R.layout.fragment_matching) {
    private val TAG = "MatchingFragment"
    private val client = OkHttpClient()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: MatchingViewModel by viewModels()

        model.lol.observe(this, { response ->
            Log.d(TAG, "request success $response")
        })

        matching_enter_queue_button.setOnClickListener {
            model.enterQueue()
        }

        matching_video_chat_button.setOnClickListener {
            startActivity(Intent(activity, VideoChatActivity::class.java))
        }
    }
}