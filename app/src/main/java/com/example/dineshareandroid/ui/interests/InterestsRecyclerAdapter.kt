package com.example.dineshareandroid.ui.interests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Interest
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.Constants
import com.google.android.material.slider.Slider

class InterestsRecyclerAdapter(_interests: MutableList<Interest>, _icons: List<Int>): RecyclerView.Adapter<InterestsRecyclerAdapter.ViewHolder>() {
    val TAG = "InterestsRecyclerAdapter"
    private var icons = _icons.toIntArray()
    var interests = _interests

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsRecyclerAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.interest_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: InterestsRecyclerAdapter.ViewHolder, position: Int) {
        holder.interestTitle.text = interests[position].name.toString().replaceFirstChar { it.titlecase() }
        holder.interestStrength.value = interests[position].strength.toFloat()
        holder.interestIcon.setImageResource(icons[position])
    }

    override fun getItemCount(): Int {
        return Constants.interestNames.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var interestIcon: ImageView = itemView.findViewById(R.id.interest_icon) as ImageView
        var interestTitle: TextView = itemView.findViewById(R.id.interest_title) as TextView
        var interestStrength: Slider = itemView.findViewById(R.id.interest_strength) as Slider

        init {
            interestStrength.addOnSliderTouchListener (object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    val newStrength = slider.value.toInt()
                    interests[adapterPosition] = interests[adapterPosition].copyOfBuilder().strength(newStrength).build()
                }
            })
        }
    }
}