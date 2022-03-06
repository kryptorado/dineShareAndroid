package com.example.dineshareandroid.ui.callLog


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.CallLog
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.interests.RecyclerAdapter
import com.example.dineshareandroid.utils.LoadingDialog
import kotlinx.android.synthetic.main.fragment_call_log.*
import kotlinx.android.synthetic.main.fragment_conversations.*


class CallLogFragment : Fragment(R.layout.fragment_call_log) {
    private val TAG = "CallLogFragment"
    private var adapter = RecyclerAdapter(mutableListOf(), listOf())
    lateinit var loader: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: CallLogViewModel by activityViewModels()
        model.callLogList.observe(this) { callLog ->
            showCallLogList(callLog)
        }

        call_log_get_button.setOnClickListener {
            model.getCallLog()

        }

    }

    private fun showCallLogList(callLog: List<CallLog>) {
        val recyclerView = conversations;
        val layoutManager = LinearLayoutManager(getActivity())
        recyclerView.layoutManager = layoutManager

        //adapter = RecyclerAdapter(callLog as MutableList<CallLog>)
        recyclerView.adapter = adapter
    }
}