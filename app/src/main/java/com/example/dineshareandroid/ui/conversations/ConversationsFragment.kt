package com.example.dineshareandroid.ui.conversations


import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dineshareandroid.MainActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.interests.InterestsActivity
import com.example.dineshareandroid.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ConversationsFragment : Fragment(R.layout.fragment_conversations) {
    private val TAG = "ConversationsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: ConversationsViewModel by activityViewModels()

        model.conversations.observe(viewLifecycleOwner) { conversations ->
            if (conversations != null) {
                Log.d(TAG, "got conversations! $conversations")
            } else {
                // display on UI "no conversations yet, get matched to start one!"
            }
        }
    }
}