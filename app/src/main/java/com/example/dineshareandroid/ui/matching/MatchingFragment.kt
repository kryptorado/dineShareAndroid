package com.example.dineshareandroid.ui.matching


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.connecting.ConnectActivity
import kotlinx.android.synthetic.main.fragment_matching.*


class MatchingFragment : Fragment(R.layout.fragment_matching) {
    private val TAG = "MatchingFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matching_button_start_call.setOnClickListener {
            startActivity(Intent(activity, ConnectActivity::class.java))
        }
    }
}