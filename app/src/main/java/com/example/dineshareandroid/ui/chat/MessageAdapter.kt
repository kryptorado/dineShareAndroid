package com.example.dineshareandroid.ui.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.ChatDataTwo
import com.example.dineshareandroid.R
import java.util.*

class MessageAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "MessageAdapter"
    private val VIEW_TYPE_USER_MESSAGE_ME = 10
    private val VIEW_TYPE_USER_MESSAGE_OTHER = 11

    private var messages: MutableList<ChatDataTwo>
    private var context: Context

    init {
        messages = ArrayList()
        this.context = context
    }

    fun loadMessages(messages: MutableList<ChatDataTwo>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    fun addFirst(message: ChatDataTwo) {
        this.messages.add(0, message)
        Log.d(TAG, "new messages size: ${messages.size}")

        notifyItemInserted(0);
//        notifyItemRangeChanged(messages.size, 0)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            VIEW_TYPE_USER_MESSAGE_ME  -> {
                MyUserHolder(layoutInflater.inflate(R.layout.item_chat_me, parent, false))
            }
            VIEW_TYPE_USER_MESSAGE_OTHER ->  {
                OtherUserHolder(layoutInflater.inflate(R.layout.item_chat_other, parent, false))
            }
            else -> MyUserHolder(layoutInflater.inflate(R.layout.item_chat_me, parent, false)) //Generic return

        }
    }

    override fun getItemViewType(position: Int): Int {

        val message = messages[position]
        if (message.senderId.equals(Amplify.Auth.currentUser.userId)) return VIEW_TYPE_USER_MESSAGE_ME
        else return VIEW_TYPE_USER_MESSAGE_OTHER
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            VIEW_TYPE_USER_MESSAGE_ME -> {
                holder as MyUserHolder
                holder.bindView(context, messages[position])
            }
            VIEW_TYPE_USER_MESSAGE_OTHER -> {
                holder as OtherUserHolder
                holder.bindView(context, messages[position])
            }
            //Handle other types of messages FILE/ADMIN ETC
        }
    }
}