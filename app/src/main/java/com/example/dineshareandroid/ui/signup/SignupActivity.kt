package com.example.dineshareandroid.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey.familyName
import com.amplifyframework.auth.AuthUserAttributeKey.givenName
import com.example.dineshareandroid.EmailConfirmationActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.CheckField
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : AppCompatActivity() {
    private val TAG = "SignupActivity"
    private val model: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setupFormListeners()

        register_button_register.setOnClickListener {
            val isFormValidated = validateForm()

            if (isFormValidated) {
                val attributes: ArrayList<AuthUserAttribute> = ArrayList()
                attributes.add(AuthUserAttribute(familyName(), signup_edit_text_first_name.text.toString()))
                attributes.add(AuthUserAttribute(givenName(), signup_edit_text_last_name.text.toString()))

                model.signup(
                    signup_edit_text_email.text.toString(),
                    signup_edit_text_password.text.toString(),
                    attributes
                )
            }
        }

        model.signupSuccess.observe(this, { success ->
            if(success) {
                val intent = Intent(this, EmailConfirmationActivity::class.java)

                intent.putExtra("firstName", signup_edit_text_first_name.text.toString())
                intent.putExtra("lastName", signup_edit_text_last_name.text.toString())
                intent.putExtra("email", signup_edit_text_email.text.toString())
                intent.putExtra("password", signup_edit_text_password.text.toString())

                startActivity(intent)

                Log.d(TAG, "Signup success")
                finish()
            }
        })

        model.signupFailedMessage.observe(this, { error ->
            Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
        })
    }

    private fun setupFormListeners() {
        signup_edit_text_first_name_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkFirstLastName(signup_edit_text_first_name_layout!!)
        }

        signup_edit_text_last_name_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkFirstLastName(signup_edit_text_last_name_layout!!)
        }

        signup_edit_text_email_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkEmail(signup_edit_text_email_layout!!)
        }

        signup_edit_text_password_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkPassword(signup_edit_text_password_layout!!)
            CheckField.checkPasswordMatches(signup_edit_text_password_layout!!, signup_edit_text_password2_layout!!)
        }

        signup_edit_text_password2_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkPasswordMatches(signup_edit_text_password_layout!!, signup_edit_text_password2_layout!!)
        }
    }

    private fun validateForm(): Boolean {
        val isFirstNameValid = CheckField.checkFirstLastName(signup_edit_text_first_name_layout)
        val isLastNameValid = CheckField.checkFirstLastName(signup_edit_text_last_name_layout)
        val isEmailValid = CheckField.checkEmail(signup_edit_text_email_layout)
        val isPassword1Valid = CheckField.checkPassword(signup_edit_text_password_layout)
        val isPassword2Valid = CheckField.checkPasswordMatches(
            signup_edit_text_password_layout,
            signup_edit_text_password2_layout
        )

        return isFirstNameValid && isLastNameValid && isEmailValid && isPassword1Valid && isPassword2Valid
    }
}