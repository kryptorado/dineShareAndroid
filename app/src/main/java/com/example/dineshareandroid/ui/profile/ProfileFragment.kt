package com.example.dineshareandroid.ui.profile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.amplifyframework.auth.AuthException
import com.amplifyframework.core.Amplify
import com.example.dineshareandroid.MainActivity
import com.example.dineshareandroid.R
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val TAG = "ProfileFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: ProfileViewModel by activityViewModels()

        model.getUser().observe(viewLifecycleOwner) { user ->
            profile_text_greeting_name.text = user.firstName.replaceFirstChar { it.titlecase() }
            profile_text_first_name_text.text = user.firstName.replaceFirstChar { it.titlecase() }
            profile_text_last_name_text.text = user.lastName.replaceFirstChar { it.titlecase() }
            profile_text_email_text.text = user.email
            Log.d(TAG, "got user! $user")
        }

        profile_button_logout.setOnClickListener {
            // TODO: Move in viewmodel
            Amplify.Auth.signOut(
                this::onLogoutSuccess,
                this::onError
            )
        }
    }

    private fun onError(error: AuthException) {
        Log.e("LoggedInActivity", "auth exception $error")
    }

    private fun onLogoutSuccess() {
        startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
    }
}