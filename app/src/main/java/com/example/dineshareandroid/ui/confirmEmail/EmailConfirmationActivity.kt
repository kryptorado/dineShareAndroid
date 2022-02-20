package com.example.dineshareandroid.ui.confirmEmail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.dineshareandroid.ui.interests.InterestsActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.CheckField
import com.example.dineshareandroid.utils.LoadingDialog
import kotlinx.android.synthetic.main.activity_email_confirmation.*


class EmailConfirmationActivity : AppCompatActivity() {
    private val TAG = "EmailConfActivity"
    private val model: EmailConfViewModel by viewModels()
    lateinit var loader: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_confirmation)
        loader = LoadingDialog(this)

        confirm_email_edit_text_code_layout?.editText?.doOnTextChanged { _, _, _, _ ->
            CheckField.checkCode(confirm_email_edit_text_code_layout!!)
        }

        email_confirm_button.setOnClickListener {
            if(CheckField.checkCode(confirm_email_edit_text_code_layout)) {
                val code = confirm_email_edit_text_code.text.toString()
                loader.show()
                model.confirmEmail(code, getEmail(), getPassword(), getFirstName(), getLastName())
            }
        }

        model.confirmSuccess.observe(this, { success ->
            if(success) {
                loader.dismiss()
                Log.d(TAG, "Confirm success")
                startActivity(Intent(this, InterestsActivity::class.java))
                finish()
            }
        })

        model.confirmFailedMessage.observe(this, { error ->
            loader.dismiss()
            Log.d(TAG, "Confirm failure")
            Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
        })
    }

    private fun getEmail(): String {
        var email = ""
        if (intent.hasExtra("email")) {
            email = intent.getStringExtra("email").toString()
        }
        return email
    }

    private fun getPassword(): String {
        var password = ""
        if (intent.hasExtra("password")) {
            password = intent.getStringExtra("password").toString()
        }
        return password
    }

    private fun getFirstName(): String {
        var firstName = ""
        if (intent.hasExtra("firstName")) {
            firstName = intent.getStringExtra("firstName").toString()
        }
        return firstName
    }

    private fun getLastName(): String {
        var lastName = ""
        if (intent.hasExtra("lastName")) {
            lastName = intent.getStringExtra("lastName").toString()
        }
        return lastName
    }
}