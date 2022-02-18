package com.example.dineshareandroid.ui.matching


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.videoChat.VideoChatActivity
import kotlinx.android.synthetic.main.fragment_matching.*


class MatchingFragment : Fragment(R.layout.fragment_matching) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matching_video_chat_button.setOnClickListener {
            startActivity(Intent(activity, VideoChatActivity::class.java))
        }
    }
}