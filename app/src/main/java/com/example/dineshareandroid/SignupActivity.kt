package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup_button.setOnClickListener {
            Amplify.Auth.signUp(
                signup_edit_text_email.text.toString(),
                signup_edit_text_password.text.toString(),
                AuthSignUpOptions.builder().userAttribute(
                    AuthUserAttributeKey.email(), signup_edit_text_email.text.toString()
                ).build(),
                this::onSignupSuccess,
                this::onSignupError
            )
        }
    }

    private fun onSignupError(error: AuthException) {
        runOnUiThread {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
        }
        Log.e("SignupActivity", "auth exception $error")
    }

    private fun onSignupSuccess(result: AuthSignUpResult) {
        val intent = Intent(this, EmailConfirmationActivity::class.java)

        intent.putExtra("email", signup_edit_text_email.text.toString())
        intent.putExtra("password", signup_edit_text_password.text.toString())

        startActivity(intent)
        Log.d("SignupActivity", "auth result $result")
    }
}