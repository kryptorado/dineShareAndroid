package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthException
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_logged_in.*


class LoggedInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        val authUser = Amplify.Auth.currentUser
        logged_in_text_username.text = authUser.username
        logged_in_text_userid.text = authUser.userId


        logged_in_button_logout.setOnClickListener {
            Amplify.Auth.signOut(
                this::onLogoutSuccess,
                this::onLoginError
            )
        }

        logged_in_button_interests.setOnClickListener {
            startActivity(Intent(this, InterestsActivity::class.java))
        }

        logged_in_button_video_call.setOnClickListener{
            startActivity(Intent(this, VideoChatActivity::class.java))
        }
    }

    private fun onLoginError(error: AuthException) {
        runOnUiThread {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
        }
        Log.e("LoggedInActivity", "auth exception $error")
    }

    private fun onLogoutSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}