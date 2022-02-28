package com.example.dineshareandroid.ui.callLog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Interest
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.Constants

class RecyclerAdapter(_interests: MutableList<Interest>, _icons: List<Int>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    val TAG = "RecyclerAdapter"
    private var icons = _icons.toIntArray()
    var interests = _interests


    init {
        // map icons to correct order
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.caller_log_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.interestTitle.text = interests[position].name
        holder.interestStrength.text = interests[position].strength.toString()
        holder.interestIcon.setImageResource(icons[position])
    }

    override fun getItemCount(): Int {
        return Constants.interestNames.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var interestIcon: ImageView = itemView.findViewById(R.id.caller_icon) as ImageView
        var interestTitle: TextView = itemView.findViewById(R.id.caller_name) as TextView
        var interestStrength: TextView = itemView.findViewById(R.id.caller_details) as TextView
    }
}