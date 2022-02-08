package com.example.dineshareandroid.utils

import com.google.android.material.textfield.TextInputLayout

object CheckField {
    fun checkEmail(inputLayout: TextInputLayout): Boolean {
        return Validation.validEmail(inputLayout)
    }
    fun checkPassword(inputLayout: TextInputLayout): Boolean {
        return Validation.validPassword(inputLayout)
    }
}