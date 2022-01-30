package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_email_confirmation.*


class EmailConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_confirmation)

        email_confirm_button.setOnClickListener {
            Amplify.Auth.confirmSignUp(
                getEmail(),
                confirm_email_edit_text_code.text.toString(),
                this::onSuccess,
                this::onLoginError
            )
        }
    }

    private fun getEmail(): String {
        var email = ""
        if (intent.hasExtra("email")) {
            email = intent.getStringExtra("email").toString()
        }
        return email
    }

    private fun getPassword(): String {
        var password = ""
        if (intent.hasExtra("password")) {
            password = intent.getStringExtra("password").toString()
        }
        return password
    }

    private fun onSuccess(result: AuthSignUpResult) {
        relogin()
        Log.d("EmailConfActivity", "onSuccess: auth result $result")
    }

    private fun onLoginSuccess(result: AuthSignInResult) {
        startActivity(Intent(this, LoggedInActivity::class.java))
        Log.d("EmailConfActivity", "onLoginSuccess: auth result $result")
    }

    private fun onLoginError(error: AuthException) {
        runOnUiThread {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
        }
        Log.e("EmailConfActivity", "onLoginError: auth exception $error")
    }

    private fun relogin() {

        Amplify.Auth.signIn(
            getEmail(),
            getPassword(),
            this::onLoginSuccess,
            this::onLoginError
        )

    }
}