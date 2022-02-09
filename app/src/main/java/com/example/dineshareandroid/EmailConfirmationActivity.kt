package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import kotlinx.android.synthetic.main.activity_email_confirmation.*


class EmailConfirmationActivity : AppCompatActivity() {
    private val TAG = "EmailConfActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_confirmation)

        email_confirm_button.setOnClickListener {
            Amplify.Auth.confirmSignUp(
                getEmail(),
                confirm_email_edit_text_code.text.toString(),
                this::onSuccess,
                this::onLoginError
            )
        }
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

    private fun onSuccess(result: AuthSignUpResult) {
        relogin()
        Log.d(TAG, "onSuccess: auth result $result")
    }

    private fun onLoginSuccess(result: AuthSignInResult) {
        writeUserToDB()
        Log.d(TAG, "onLoginSuccess: auth result $result")
        startActivity(Intent(this, InterestsActivity::class.java))
        finish()
    }

    private fun onLoginError(error: AuthException) {
        runOnUiThread {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
        }
        Log.e(TAG, "onLoginError: auth exception $error")
    }

    private fun writeUserToDB() {
        val interests = mutableListOf<Int>()
        interests.addAll(mutableListOf(1, 2, 3)) // TODO: don't hardcode all the interests

        val user = User.builder()
            .id(Amplify.Auth.currentUser.userId)
            .firstName(getFirstName())
            .lastName(getLastName())
            .interests(interests)
            .email(getEmail())
            .build()

        Amplify.API.mutate(
            ModelMutation.create(user),
            { Log.i(TAG, "CREATE user succeeded: $it") },
            { Log.e(TAG, "CREATE user failed", it) }
        )
    }

    private fun relogin() {
        Amplify.Auth.signIn(
            getEmail(),
            getPassword(),
            this::onLoginSuccess,
            this::onLoginError
        )
    }
}