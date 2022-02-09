package com.example.dineshareandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import com.example.dineshareandroid.InterestsActivity
import com.example.dineshareandroid.LoggedInActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.signup.SignupActivity
import com.example.dineshareandroid.utils.CheckField
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val model: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupFormListeners()

        login_button_login.setOnClickListener {
            if (isFormValid()) {
                model.login(
                    login_edit_text_email.text.toString(),
                    login_edit_text_password.text.toString()
                )
            }
        }

        google_login_button.setOnClickListener {
//            model.loginWithGoogle()
            loginWithGoogle()
        }

        model.loginSuccess.observe(this, { success ->
            if(success) {
                Log.d(TAG, "Login success")
                startActivity(Intent(this, LoggedInActivity::class.java))
                finish()
            }
        })

        model.loginFailedMessage.observe(this, { error ->
            Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
        })

        model.isNewUser.observe(this, {isNewUser ->
            if (isNewUser) {
                startActivity(Intent(this, InterestsActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoggedInActivity::class.java))
                finish()
            }
        })

        login_button_register.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun setupFormListeners() {
        login_edit_text_email_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkEmail(login_edit_text_email_layout!!)
        }

        login_edit_text_password_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkPassword(login_edit_text_password_layout!!)
        }
    }

    private fun isFormValid(): Boolean {
        val isEmailValid = CheckField.checkEmail(login_edit_text_email_layout)
        val isPasswordValid = CheckField.checkPassword(login_edit_text_password_layout)
        return isEmailValid && isPasswordValid
    }

    private fun loginWithGoogle() {
        Amplify.Auth.signInWithSocialWebUI(
            AuthProvider.google(), this,
            {
                Log.i(TAG, "Sign in with Google OK: $it")
                runOnUiThread {
                    progress_bar.visibility = View.VISIBLE
                }
                model.isNewUser()
            },
            { Log.e(TAG, "Sign in with Google failed", it) }
        )
    }

}