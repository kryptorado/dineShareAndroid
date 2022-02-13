package com.example.dineshareandroid.utils

import com.google.android.material.textfield.TextInputLayout

object CheckField {
    fun checkEmail(inputLayout: TextInputLayout): Boolean {
        return Validation.validEmail(inputLayout)
    }

    fun checkPassword(inputLayout: TextInputLayout): Boolean {
        return Validation.validPassword(inputLayout)
    }

    fun checkPasswordMatches(password1: TextInputLayout, password2: TextInputLayout): Boolean {
        return Validation.passwordMatches(password1, password2)
    }

    fun checkFirstLastName(inputLayout: TextInputLayout): Boolean {
        return Validation.validFirstLastname(inputLayout)
    }

    fun checkCode(code: TextInputLayout): Boolean {
        return Validation.validCode(code)
    }
}