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

    fun validPassword(textInputLayout: TextInputLayout): Boolean {
        if (emptyField(textInputLayout)) {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.empty_field)
            return false
        } else {
            val passwordPattern = "^[A-Za-z0-9]{8,20}$"
            val textContent = textInputLayout.editText?.text.toString()
            val matcher: Matcher = Pattern.compile(passwordPattern).matcher(textContent)

            if (matcher.matches()) {
                textInputLayout.error = null
                return true
            } else {
                textInputLayout.error =
                    textInputLayout.context.resources.getString(R.string.invalid_password)
                textInputLayout.editText?.requestFocus()
                return false
            }
        }
    }

    private fun emptyField(textInputLayout: TextInputLayout): Boolean {
        return textInputLayout.editText?.text.toString().isEmpty()
    }
}