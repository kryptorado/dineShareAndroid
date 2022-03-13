package com.example.dineshareandroid.ui.postCall

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dineshareandroid.ui.connecting.ConnectActivity
import com.example.dineshareandroid.ui.conversations.ConversationsFragment
import com.example.dineshareandroid.ui.loggedIn.LoggedInActivity
import com.example.dineshareandroid.ui.report.ReportActivity
import kotlinx.android.synthetic.main.activity_post_call.*


class PostCallActivity : AppCompatActivity() {
    private val model: PostCallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.dineshareandroid.R.layout.activity_post_call)

        var callLength = intent.getStringExtra("channelName").toString()
        var remoteUserFirstName = intent.getStringExtra("remoteUserFirstName").toString()
        var remoteUserLastName = intent.getStringExtra("remoteUserLastName").toString()
        var channelName = intent.getStringExtra("channelName").toString()
        var remoteUserId = intent.getStringExtra("remoteUserId").toString()

        if (callLength != "") {
            callLength = intent.getStringExtra("callLength").toString()
//            Toast.makeText(this, callLength, Toast.LENGTH_SHORT).show()
        }

        if (remoteUserFirstName != "") {
            remoteUserFirstName = intent.getStringExtra("remoteUserFirstName").toString().replaceFirstChar { it.titlecase() }
            post_call_text_title.text = "Connect with $remoteUserFirstName"
            post_call_button_messages.text = "Message $remoteUserFirstName"
//            Toast.makeText(this, remoteUserFirstName, Toast.LENGTH_SHORT).show()
        }

        if (remoteUserLastName != "") {
            remoteUserLastName = intent.getStringExtra("remoteUserLastName").toString().replaceFirstChar { it.titlecase() }
//            Toast.makeText(this, remoteUserLastName, Toast.LENGTH_SHORT).show()
        }

        if (remoteUserId != "") {
            remoteUserId = intent.getStringExtra("remoteUserId").toString()
//            Toast.makeText(this, remoteUserId, Toast.LENGTH_SHORT).show()
        }

        if (channelName != "") {
            channelName = intent.getStringExtra("channelName").toString()
//            Toast.makeText(this, channelName, Toast.LENGTH_SHORT).show()
        }

        model.createCallLog(remoteUserFirstName, remoteUserLastName, callLength)
        model.createChatRoom(channelName, remoteUserFirstName, remoteUserLastName, remoteUserId)

        report_user.setOnClickListener {
            // pass other user id to activity
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra("remoteUserId", remoteUserId)
            startActivity(intent)
        }

        post_call_button_messages.setOnClickListener {
            val intent = Intent(this, LoggedInActivity::class.java)
            intent.putExtra("EXTRA", "openMessages");
            startActivity(intent);
            finish()
        }

        post_call_button_match.setOnClickListener {
            startActivity(Intent(this, ConnectActivity::class.java))
            finish()
        }

    }
}