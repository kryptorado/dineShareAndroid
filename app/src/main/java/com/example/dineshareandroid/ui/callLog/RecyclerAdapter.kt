package com.example.dineshareandroid.ui.callLog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.CallLog
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.Constants

class RecyclerAdapter(_callLog: MutableList<CallLog>, _icons: List<Int>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    val TAG = "RecyclerAdapter"
    private var icons = _icons.toIntArray()
    var callLog = _callLog


    init {
        // map icons to correct order
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.caller_log_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        //holder.callerIcon.setImageResource = callLog[position].name
        holder.callerName.text = callLog[position].calleeName
        holder.callerDetails.text = callLog[position].duration
    }

    override fun getItemCount(): Int {
        return Constants.interestNames.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        //var callerIcon: ImageView = itemView.findViewById(R.id.caller_icon) as ImageView
        var callerName: TextView = itemView.findViewById(R.id.caller_name) as TextView
        var callerDetails: TextView = itemView.findViewById(R.id.caller_details) as TextView
    }
}