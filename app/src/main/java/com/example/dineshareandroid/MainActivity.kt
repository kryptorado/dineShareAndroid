package com.example.dineshareandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amplifyframework.core.Amplify
import com.example.dineshareandroid.ui.loggedIn.LoggedInActivity
import com.example.dineshareandroid.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = Amplify.Auth.currentUser

        if (currentUser == null) {
            // go to login screen
            startActivity(Intent(this, LaunchScreenActivity::class.java))
            finish()
        } else {
            // go to logged in screen
            startActivity(Intent(this, LoggedInActivity::class.java))
            finish()
        }
    }

}