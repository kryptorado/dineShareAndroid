package com.example.dineshareandroid.ui.profile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dineshareandroid.MainActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.interests.InterestsActivity
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val TAG = "ProfileFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: ProfileViewModel by activityViewModels()

        model.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                profile_text_greeting_name.text = user.firstName.replaceFirstChar { it.titlecase() }
                profile_text_first_name_text.text = user.firstName.replaceFirstChar { it.titlecase() }
                profile_text_last_name_text.text = user.lastName.replaceFirstChar { it.titlecase() }
                profile_text_email_text.text = user.email
                Log.d(TAG, "got user! $user")
            }
        }

        profile_button_logout.setOnClickListener {
            // TODO: check and handle if error occurs
            model.logoutUser()
        }

        profile_button_edit_profile.setOnClickListener {
            startActivity(Intent(activity, InterestsActivity::class.java))
        }

        model.logoutSuccess.observe(viewLifecycleOwner) {success ->
            if (success) {
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
        }
    }
}