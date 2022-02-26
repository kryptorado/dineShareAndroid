package com.example.dineshareandroid.ui.callLog


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dineshareandroid.R
import kotlinx.android.synthetic.main.fragment_call_log.*


class CallLogFragment : Fragment(R.layout.fragment_call_log) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: CallLogViewModel by activityViewModels()

//        call_log_create_button.setOnClickListener {
//            model.createCallLog()
//        }

        call_log_get_button.setOnClickListener {
            model.getCallLog()
        }

    }
}