package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_logged_in.*


class LoggedInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

       Amplify.Auth.fetchUserAttributes(
            this::onSuccess,
            this::onLoginError
        )

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

    private fun onSuccess(users: List<AuthUserAttribute>) {
        val attrMap = users.map { it.key to it.value }.toMap()
        // TODO: add user to db
        Log.d("attrMap", "user value ${attrMap}")

        for (user in users) {
            Log.d("LoggedInActivity", "user value ${user.key.equals("family_name")}")

            Log.d("LoggedInActivity", "user value ${user.value}")

            Log.d("LoggedInActivity", "auth exception $user")

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