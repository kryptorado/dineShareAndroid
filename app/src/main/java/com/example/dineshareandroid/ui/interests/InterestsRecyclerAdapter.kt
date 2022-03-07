package com.example.dineshareandroid.ui.interests

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

class InterestsRecyclerAdapter(_interests: MutableList<Interest>, _icons: List<Int>): RecyclerView.Adapter<InterestsRecyclerAdapter.ViewHolder>() {
    val TAG = "RecyclerAdapter"
    private var icons = _icons.toIntArray()
    var interests = _interests

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsRecyclerAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.interest_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: InterestsRecyclerAdapter.ViewHolder, position: Int) {
        holder.interestTitle.text = interests[position].name
        holder.interestStrength.progress = interests[position].strength
        holder.interestIcon.setImageResource(icons[position])
    }

    override fun getItemCount(): Int {
        return Constants.interestNames.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var interestIcon: ImageView = itemView.findViewById(R.id.interest_icon) as ImageView
        var interestTitle: TextView = itemView.findViewById(R.id.interest_title) as TextView
        var interestStrength: SeekBar = itemView.findViewById(R.id.interest_strength) as SeekBar

        init {
            interestStrength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    // an interest strength has to be 1 minimum
                    var strength = p1
                    if (p1 == 0) {
                       strength = 1

                    }
                    interests[adapterPosition] = interests[adapterPosition].copyOfBuilder().strength(strength).build()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            })
        }
    }
}