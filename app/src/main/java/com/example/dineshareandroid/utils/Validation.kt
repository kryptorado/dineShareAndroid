package com.example.dineshareandroid.utils

import com.example.dineshareandroid.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object Validation {
    fun validEmail(
        textInputLayout: TextInputLayout): Boolean {

        if (emptyField(textInputLayout)) {
            textInputLayout.error = textInputLayout.context.resources.getString(R.string.empty_field)
            return false
        } else {
            val validEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            val textContent = textInputLayout.editText?.text.toString().lowercase(Locale.ROOT)
            val matcherEmail: Matcher = Pattern.compile(validEmail).matcher(textContent)

            if (matcherEmail.matches()) {
                textInputLayout.error = null
                return true
            } else {
                textInputLayout.error =
                    textInputLayout.context.resources.getString(R.string.invalid_email)
                textInputLayout.editText?.requestFocus()
                return false
            }
        }
    }

    fun validPassword(passwordInputLayout: TextInputLayout): Boolean {
        if (emptyField(passwordInputLayout)) {
            passwordInputLayout.error =
                passwordInputLayout.context.resources.getString(R.string.empty_field)
            return false
        } else {
            val passwordPattern = "^[A-Za-z0-9]{8,20}$"
            val textContent = passwordInputLayout.editText?.text.toString()
            val matcher: Matcher = Pattern.compile(passwordPattern).matcher(textContent)

            if (matcher.matches()) {
                passwordInputLayout.error = null
                return true
            } else {
                passwordInputLayout.error =
                    passwordInputLayout.context.resources.getString(R.string.invalid_password)
                passwordInputLayout.editText?.requestFocus()
                return false
            }
        }
    }

    fun passwordMatches(password1: TextInputLayout, password2: TextInputLayout): Boolean {
        if (emptyField(password2)) {
            password2.error =
                password2.context.resources.getString(R.string.empty_field)
            return false
        } else {
            val password1Content = password1.editText?.text.toString()
            val password2Content = password2.editText?.text.toString()

            return if (password1Content != password2Content) {
                password2.error = password2.context.resources.getString(R.string.not_matching_password)
                false
            } else {
                password2.error = null
                return true
            }
        }
    }

    fun validFirstLastname(textInputLayout: TextInputLayout): Boolean {
        if (emptyField(textInputLayout)) {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.empty_field)
            return false
        } else {
            val firstNamePattern = "^(?=.{1,30}\$)[a-zA-Z]+(?:'[a-zA-Z]+)*(?:[- ][a-zA-Z]+(?:'[a-zA-Z])*)*\$"
            val textContent = textInputLayout.editText?.text.toString()
            val matcher: Matcher = Pattern.compile(firstNamePattern).matcher(textContent)

            if (matcher.matches()) {
                textInputLayout.error = null
                return true
            } else {
                textInputLayout.error =
                    textInputLayout.context.resources.getString(R.string.invalid_first_last_name)
                textInputLayout.editText?.requestFocus()
                return false
            }
        }
    }

    private fun emptyField(textInputLayout: TextInputLayout): Boolean {
        return textInputLayout.editText?.text.toString().isEmpty()
    }
}