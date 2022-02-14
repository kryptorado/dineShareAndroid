package com.example.dineshareandroid.ui.conversations


import android.os.Bundle

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.example.dineshareandroid.R


class ConversationsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_conversations, container, false)


        return view
    }
}