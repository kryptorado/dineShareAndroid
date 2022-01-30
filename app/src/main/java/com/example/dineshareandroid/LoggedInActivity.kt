package com.example.dineshareandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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


//        Amplify.Auth.fetchUserAttributes(
//            { attributes ->
//
//                Log.i("AuthDemo", "User attributes = ${attributes}")
//            },
//            { Log.e("AuthDemo", "Failed to fetch user attributes", it) }
//        )

        logged_in_button_logout.setOnClickListener {
            Amplify.Auth.signOut(
                this::onLogoutSuccess,
                this::onLoginError
            )
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