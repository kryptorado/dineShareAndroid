package com.example.dineshareandroid.ui.callLog

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.CallLog
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.LoadingDialog
import kotlinx.android.synthetic.main.fragment_call_log.*


class CallLogFragment : Fragment(R.layout.fragment_call_log) {
    private val TAG = "CallLogFragment"
    private var adapter = CallLogRecyclerAdapter(mutableListOf())
    lateinit var loader: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: CallLogViewModel by activityViewModels()
        model.getCallLog()

        model.callLogList.observe(this) { callLog ->
            showCallLogList(callLog)
        }
    }

    private fun showCallLogList(callLog: List<CallLog>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.callLogs_recyclerview)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = layoutManager

        adapter = CallLogRecyclerAdapter(callLog as MutableList<CallLog>)
        recyclerView?.adapter = adapter
        ViewCompat.setNestedScrollingEnabled(callLogs_recyclerview, false);

    }
}