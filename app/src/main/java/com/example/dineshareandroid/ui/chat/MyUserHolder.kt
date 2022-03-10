package com.example.dineshareandroid.ui.chat

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.ChatData
import kotlinx.android.synthetic.main.item_chat_me.view.*

class MyUserHolder(view: View) : RecyclerView.ViewHolder(view) {

    val messageText = view.text_gchat_message_me
    val date = view.text_gchat_date_me
    val messageDate = view.text_gchat_timestamp_me

    fun bindView(context: Context, message: ChatData) {

        messageText.text = message.message
//        messageDate.text = DateUtil.formatTime(message.createdAt)
        messageDate.text = "TODO: Date"

        date.visibility = View.VISIBLE
//        date.text = DateUtil.formatDate(message.createdAt)
        date.text = "TODO: DATE"
    }
}