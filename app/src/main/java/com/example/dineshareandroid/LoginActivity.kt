package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import com.example.dineshareandroid.utils.CheckField
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val isFormValidated = validateForm()

            if (isFormValidated) {
                Amplify.Auth.signIn(
                    login_edit_text_email.text.toString(),
                    login_edit_text_password.text.toString(),
                    this::onLoginSuccess,
                    this::onLoginError
                )
            }
        }

        login_edit_text_email_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkEmail(login_edit_text_email_layout!!)
        }

        login_edit_text_password_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkPassword(login_edit_text_password_layout!!)
        }

        login_button_signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        google_login_button.setOnClickListener {
            Amplify.Auth.signInWithSocialWebUI(
                AuthProvider.google(), this,
                {
                    startActivity(Intent(this, LoggedInActivity::class.java))
                    Log.i("AuthQuickstart", "Sign in OK: $it")
                },
                { Log.e("AuthQuickstart", "Sign in failed", it) }
            )
        }
    }

    private fun validateForm(): Boolean {
        val isEmailValid = CheckField.checkEmail(login_edit_text_email_layout)
        val isPasswordValid = CheckField.checkPassword(login_edit_text_password_layout)
        return isEmailValid && isPasswordValid
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