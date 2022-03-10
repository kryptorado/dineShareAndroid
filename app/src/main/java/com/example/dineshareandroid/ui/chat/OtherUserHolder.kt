package com.example.dineshareandroid.ui.chat

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.ChatData
import com.amplifyframework.datastore.generated.model.ChatDataTwo
import kotlinx.android.synthetic.main.item_chat_other.view.*


class OtherUserHolder(view: View) : RecyclerView.ViewHolder(view) {

    val messageText = view.text_gchat_message_other
    val date = view.text_gchat_date_other
    val timestamp = view.text_gchat_timestamp_other
    val profileImage = view.image_gchat_profile_other
//    val user = view.text_gchat_user_other


    fun bindView(context: Context, message: ChatDataTwo) {

        messageText.text = message.message

        if (message.createdAt != null) {
            timestamp.text = DateUtil.formatTime(message.createdAt.toDate().time)
            date.text = DateUtil.formatDate(message.createdAt.toDate().time)
        } else {
            timestamp.text = DateUtil.formatTime(System.currentTimeMillis())
            date.text = DateUtil.formatDate(System.currentTimeMillis())
        }
    }
}
