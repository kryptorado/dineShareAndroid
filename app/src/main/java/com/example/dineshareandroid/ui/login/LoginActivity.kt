package com.example.dineshareandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import com.example.dineshareandroid.ui.interests.InterestsActivity
import com.example.dineshareandroid.ui.loggedIn.LoggedInActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.resetPassword.ResetPasswordActivity
import com.example.dineshareandroid.ui.signup.SignupActivity
import com.example.dineshareandroid.ui.termsConditions.TermsConditionsActivity
import com.example.dineshareandroid.utils.CheckField
import com.example.dineshareandroid.utils.LoadingDialog
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private val model: LoginViewModel by viewModels()
    lateinit var loader: LoadingDialog


    // workaround for catching UserCancelledException
    // https://github.com/aws-amplify/amplify-android/issues/1536
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) {
            loader.dismiss()
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupFormListeners()

        loader = LoadingDialog(this)

        login_button_login.setOnClickListener {
            if (isFormValid()) {
                loader.show()
                model.login(
                    login_edit_text_email.text.toString(),
                    login_edit_text_password.text.toString()
                )
            }
        }

        google_login_button.setOnClickListener {
            loader.show()
            Amplify.Auth.signInWithSocialWebUI(
                AuthProvider.google(), this,
                {
                    model.ssoLogin()
                    Log.i(TAG, "Sign in with Google OK: $it")
                },
                {
                    runOnUiThread{
                        loader.dismiss()
                    }
                    Log.e(TAG, "Sign in with Google failed", it)
                }
            )
        }

        model.loginSuccess.observe(this, { success ->
            if(success) {
                loader.dismiss()
                Log.d(TAG, "Login success")
                startActivity(Intent(this, LoggedInActivity::class.java))
                finish()
            } else {
                // TODO: check if user unverified error was thrown and if yes,
                // go through flow to verify user
            }
        })

        model.loginFailedMessage.observe(this, { error ->
            loader.dismiss()
            Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
        })

        model.isNewUser.observe(this, {isNewUser ->
            if (isNewUser) {

                model.isUniqueEmail.observe(this, {isUniqueEmail ->
                    if (isUniqueEmail) {
                        loader.dismiss()
                        startActivity(Intent(this, InterestsActivity::class.java))
                        finish()
                    } else {
                        Amplify.Auth.signOut(
                            this::onLogoutSuccess,
                            this::onError
                        )
                        loader.dismiss()
                        Toast.makeText(applicationContext, "User with this email already exists", Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                loader.dismiss()
                startActivity(Intent(this, LoggedInActivity::class.java))
                finish()
            }
        })

        login_button_register.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        login_terms_conditions.setOnClickListener {
            startActivity(Intent(this, TermsConditionsActivity::class.java))
        }

        login_text_forgot_password.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
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

    private fun onError(error: AuthException) {
        runOnUiThread {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
        }
        Log.e("LoggedInActivity", "auth exception $error")
    }

    private fun onLogoutSuccess() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}