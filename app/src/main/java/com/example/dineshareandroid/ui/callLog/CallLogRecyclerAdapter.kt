package com.example.dineshareandroid.ui.callLog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.CallLog
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.dineshareandroid.R
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.internal.format
import java.util.*


class CallLogRecyclerAdapter(_callLog: MutableList<CallLog>, private var itemClickListener: ItemClickListener): RecyclerView.Adapter<CallLogRecyclerAdapter.ViewHolder>() {
    val TAG = "CallLogRecyclerAdapter"
//    private var icons = _icons.toIntArray()
    var callLog = _callLog

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    //var modelLog = _modelLog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogRecyclerAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.caller_log_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CallLogRecyclerAdapter.ViewHolder, position: Int) {
        //holder.callerIcon.setImageResource = callLog[position].name
        holder.callerName.text = callLog[position].calleeName

        var formattedDuration = callLog[position].duration.replace("hours,", "h")
                                                        .replace("minutes and", "m")
                                                        .replace("seconds.", "s");
        var date = callLog[position].updatedAt.toString().replace("Temporal.DateTime{offsetDateTime='", "")
                                                        .replace("Z'}", "")
                                                        .take(10)
        holder.callerDetails.text = date
        holder.callDuration.text = formattedDuration

        val generator = ColorGenerator.MATERIAL
        //val color = generator.getColor(user.email)
        //String manipulation to get first letter of first name and last name
        val color = generator.getColor(callLog[position].calleeName)
        val drawable = TextDrawable.builder()
            .buildRect(callLog[position].calleeName.first().toString(), color)

        val image: ImageView = holder.callerIcon
        image.setImageDrawable(drawable)

        holder.callerDelete.setOnClickListener(View.OnClickListener {
            itemClickListener.onItemClick(position)
            callLog.removeAt(position) // remove the item from list
            notifyItemRemoved(position) // notify the adapter about the removed item
            notifyDataSetChanged()
        })
    }

    override fun getItemCount(): Int {
        return callLog.size
    }

    fun clearAll() {
        callLog.clear()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        callLog.removeAt(position)
//        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var callerIcon: ImageView = itemView.findViewById(R.id.contact_icon) as ImageView
        var callerName: TextView = itemView.findViewById(R.id.call_log_text_name) as TextView
        var callerDetails: TextView = itemView.findViewById(R.id.call_log_text_date) as TextView
        var callDuration: TextView = itemView.findViewById(R.id.call_log_text_duration) as TextView
        var callerDelete: ImageButton = itemView.findViewById(R.id.deleteLog) as ImageButton
    }
}