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
    lateinit var loader: LoadingDialog
    private val model: CallLogViewModel by activityViewModels()
    private var adapter = CallLogRecyclerAdapter(mutableListOf(), object : CallLogRecyclerAdapter.ItemClickListener {
        override fun onItemClick(position: Int) {
            // this function will handle the item click on a provided position
            deleteCallLog(position)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: CallLogViewModel by activityViewModels()
        model.getCallLog()

        model.callLogList.observe(this) { callLog ->
            showCallLogList(ArrayList<CallLog>(callLog))
        }

        deleteAllLogs.setOnClickListener(View.OnClickListener {
            for (i in adapter.itemCount - 1 downTo 0) {
                model.deleteCallLog(adapter.callLog[i])
            }
            adapter.clearAll()
            if (adapter.callLog.isEmpty()) {
                call_log_is_empty.visibility = View.VISIBLE
                deleteAllLogs.visibility = View.GONE
            } else {
                call_log_is_empty.visibility = View.GONE
                deleteAllLogs.visibility = View.VISIBLE
            }
        })
    }

    private fun showCallLogList(callLog: List<CallLog>) {
        if (callLog.isEmpty()) {
            call_log_is_empty.visibility = View.VISIBLE
            deleteAllLogs.visibility = View.GONE
        } else {
            call_log_is_empty.visibility = View.GONE
            deleteAllLogs.visibility = View.VISIBLE
        }


        val recyclerView = view?.findViewById<RecyclerView>(R.id.callLogs_recyclerview)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = layoutManager

        adapter = CallLogRecyclerAdapter(callLog as MutableList<CallLog>, object : CallLogRecyclerAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                // this function will handle the item click on a provided position
                deleteCallLog(position)
            }
        })
        recyclerView?.adapter = adapter
        ViewCompat.setNestedScrollingEnabled(callLogs_recyclerview, false);

    }

    private fun deleteCallLog(position: Int) {
        model.deleteCallLog(adapter.callLog[position])
    }
}