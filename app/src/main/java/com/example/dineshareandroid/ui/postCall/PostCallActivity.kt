package com.example.dineshareandroid.ui.postCall

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.login.LoginActivity
import com.example.dineshareandroid.ui.report.ReportActivity
import kotlinx.android.synthetic.main.activity_launch_screen.*
import kotlinx.android.synthetic.main.activity_post_call.*

class PostCallActivity : AppCompatActivity() {
    private val model: PostCallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_call)

        var callLength = intent.getStringExtra("channelName").toString()
        var remoteUserName = intent.getStringExtra("remoteUserName").toString()
        var channelName = intent.getStringExtra("channelName").toString()
        var remoteUserId = intent.getStringExtra("remoteUserId").toString()

        if (callLength != "") {
            callLength = intent.getStringExtra("callLength").toString()
//            Toast.makeText(this, callLength, Toast.LENGTH_SHORT).show()
        }

        if (remoteUserName != "") {
            remoteUserName = intent.getStringExtra("remoteUserName").toString()
            post_call_text_title.text = "Connect with ${remoteUserName!!.replaceFirstChar { it.titlecase() }}"
            post_call_button_messages.text = "Message ${remoteUserName!!.replaceFirstChar { it.titlecase() }}"
            Toast.makeText(this, remoteUserName, Toast.LENGTH_SHORT).show()
        }

        if (remoteUserId != "") {
            remoteUserId = intent.getStringExtra("remoteUserId").toString()
            Toast.makeText(this, remoteUserId, Toast.LENGTH_SHORT).show()
        }

        if (channelName != "") {
            channelName = intent.getStringExtra("channelName").toString()
            Toast.makeText(this, channelName, Toast.LENGTH_SHORT).show()
        }

//        model.createCallLog(remoteUserName, callLength)
        model.createChatRoom(channelName, remoteUserName, remoteUserId)

        report_user.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }

    }
}