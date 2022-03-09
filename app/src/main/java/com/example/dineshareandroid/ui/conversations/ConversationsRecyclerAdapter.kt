package com.example.dineshareandroid.ui.conversations

import android.content.Context
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
    var chatRooms = _chatRooms

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationsRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chatroom_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ConversationsRecyclerAdapter.ViewHolder, position: Int) {
//        holder.chatRoomId.text = chatRooms[position].chatRoomId
        holder.chatRoomLatestText.text = "TODO: Get latest text"
        holder.chatRoomOtherUserName.text = chatRooms[position].otherUserName
        holder.chatRoomDate.text = chatRooms[position].createdAt.toString().replace("Temporal.DateTime{offsetDateTime='", "")
            .replace("Z'}", "")
            .take(10)

        holder.itemView.setOnClickListener {
            val context: Context = holder.itemView.context
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("channelName", chatRooms[position].chatRoomId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chatRooms.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var chatRoomLatestText: TextView = itemView.findViewById(R.id.chatroom_card_latest_text) as TextView
        var chatRoomOtherUserName: TextView = itemView.findViewById(R.id.chatroom_card_otherUserName) as TextView
        var chatRoomDate: TextView = itemView.findViewById(R.id.chatroom_card_date) as TextView
    }
}