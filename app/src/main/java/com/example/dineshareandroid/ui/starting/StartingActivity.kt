package com.example.dineshareandroid.ui.starting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.login.LoginActivity
import com.example.dineshareandroid.ui.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_starting.*


class StartingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)

        starting_button_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        starting_button_register.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}