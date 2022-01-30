package com.example.dineshareandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amplifyframework.core.Amplify

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = Amplify.Auth.currentUser

        if (currentUser == null) {
            // go to login screen
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            // go to logged in screen
            startActivity(Intent(this, LoggedInActivity::class.java))
        }
    }
}