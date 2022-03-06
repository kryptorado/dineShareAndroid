package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import com.example.dineshareandroid.ui.interests.InterestsActivity
import com.example.dineshareandroid.ui.loggedIn.LoggedInActivity
import com.example.dineshareandroid.ui.login.LoginActivity
import com.example.dineshareandroid.ui.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_launch_screen.*
import kotlinx.android.synthetic.main.activity_login.*

class LaunchScreenActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        log_in_button.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        register_button.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}