package com.example.dineshareandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_login.login_button_login
import kotlinx.android.synthetic.main.activity_login.login_button_signup
import kotlinx.android.synthetic.main.activity_login.login_edit_text_email
import kotlinx.android.synthetic.main.activity_login.login_edit_text_password



class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            Amplify.Auth.signIn(
                login_edit_text_email.text.toString(),
                login_edit_text_password.text.toString(),
                this::onLoginSuccess,
                this::onLoginError
            )
        }

        login_button_signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun onLoginError(error: AuthException) {
        runOnUiThread {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
        }
        Log.e("LoginActivity", "auth exception $error")
    }

    private fun onLoginSuccess(result: AuthSignInResult) {
        startActivity(Intent(this, LoggedInActivity::class.java))
        Log.d("LoginActivity", "auth result $result")
    }
}