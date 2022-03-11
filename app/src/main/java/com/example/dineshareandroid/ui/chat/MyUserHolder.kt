package com.example.dineshareandroid.ui.chat

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.ChatDataTwo
import kotlinx.android.synthetic.main.item_chat_me.view.*

class MyUserHolder(view: View) : RecyclerView.ViewHolder(view) {

    val messageText = view.text_gchat_message_me
    val date = view.text_gchat_date_me
    val messageDate = view.text_gchat_timestamp_me

    fun bindView(context: Context, message: ChatDataTwo) {
        messageText.text = message.message

        if (message.createdAt != null) {
            messageDate.text = DateUtil.formatTime(message.createdAt.toDate().time)
            date.text = DateUtil.formatDate(message.createdAt.toDate().time)
        } else {
            messageDate.text = DateUtil.formatTime(System.currentTimeMillis())
            date.text = DateUtil.formatDate(System.currentTimeMillis())
        }
    }
}