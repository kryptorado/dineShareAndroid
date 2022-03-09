package com.example.dineshareandroid.ui.conversations

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.ChatRoom
import com.example.dineshareandroid.R
import android.content.Intent
import com.example.dineshareandroid.ui.chat.ChatActivity


class ConversationsRecyclerAdapter(_chatRooms: MutableList<ChatRoom>): RecyclerView.Adapter<ConversationsRecyclerAdapter.ViewHolder>() {
    val TAG = "ConvRecyclerAdapter"
//    private var icons = _icons.toIntArray()
    var chatRooms = _chatRooms

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationsRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chatroom_preview_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ConversationsRecyclerAdapter.ViewHolder, position: Int) {
        holder.chatRoomId.text = chatRooms[position].chatRoomId

        holder.itemView.setOnClickListener {
            Log.d(TAG, "item was clicked ${chatRooms[position]}")
            val context: Context = holder.itemView.context
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("channelName", chatRooms[position].chatRoomId)
            context.startActivity(intent)
        }
//        holder.interestStrength.progress = chatRooms[position].strength
//        holder.interestIcon.setImageResource(chatRooms[position])
    }


    override fun getItemCount(): Int {
        return chatRooms.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var chatRoomId: ImageView = itemView.findViewById(R.id.interest_icon) as ImageView
        var chatRoomId: TextView = itemView.findViewById(R.id.chatroom_card_otherUserName) as TextView
    }
}