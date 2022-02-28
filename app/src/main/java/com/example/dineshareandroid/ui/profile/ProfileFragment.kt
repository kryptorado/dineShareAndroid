package com.example.dineshareandroid.ui.profile


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dineshareandroid.MainActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.interests.InterestsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import android.widget.ImageView
import com.amulyakhare.textdrawable.TextDrawable


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val TAG = "ProfileFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: ProfileViewModel by activityViewModels()

        model.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                var name = user.firstName + user.lastName
                profile_text_name_text.text = name
                profile_text_email_text.text = user.email
                Log.d(TAG, "got user! $user")
            }
        }

        profile_button_logout.setOnClickListener {
            model.logoutUser()
        }

        profile_button_edit_profile.setOnClickListener {
            startActivity(Intent(activity, InterestsActivity::class.java))
        }

        model.logoutSuccess.observe(viewLifecycleOwner) {success ->
            if (success) {
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            } else {
                Toast.makeText(context, "Couldn't log out", Toast.LENGTH_SHORT).show()
            }
        }

        val drawable = TextDrawable.builder()
            .buildRect("A", Color.GREEN)

        val image: ImageView = view.findViewById(R.id.shapeableImageView)
        image.setImageDrawable(drawable)
    }
}