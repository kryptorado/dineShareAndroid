package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import com.example.dineshareandroid.backend.UserData
import com.example.dineshareandroid.ui.signup.SignupActivity
import com.example.dineshareandroid.utils.CheckField
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*


class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_edit_text_email_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkEmail(login_edit_text_email_layout!!)
        }

        login_edit_text_password_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkPassword(login_edit_text_password_layout!!)
        }

        login_button_login.setOnClickListener {
            login()
        }

        login_button_register.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        google_login_button.setOnClickListener {
            loginWithGoogle()
        }
    }

    private fun validateForm(): Boolean {
        val isEmailValid = CheckField.checkEmail(login_edit_text_email_layout)
        val isPasswordValid = CheckField.checkPassword(login_edit_text_password_layout)
        return isEmailValid && isPasswordValid
    }

    private fun login () {
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

    private fun loginWithGoogle() {
        Amplify.Auth.signInWithSocialWebUI(
            AuthProvider.google(), this,
            {
                Log.i(TAG, "Sign in with Google OK: $it")

                // do some checks before logging them in
                // first time auth?
//                lifecycleScope.launch {
                UserData.getUserProfile()
//                }
                startActivity(Intent(this, LoggedInActivity::class.java))
                finish()
            },
            { Log.e(TAG, "Sign in with Google failed", it) }
        )
    }

    private fun onLoginError(error: AuthException) {
        runOnUiThread {
            Toast.makeText(applicationContext, (error.cause as AmazonServiceException).errorMessage, Toast.LENGTH_LONG).show()
        }
        Log.e(TAG, "Login with Cognito failed $error")
    }

    private fun onLoginSuccess(result: AuthSignInResult) {
        Log.d(TAG, "Login with Cognito success $result")
        startActivity(Intent(this, LoggedInActivity::class.java))
        finish()
    }
}