package com.example.dineshareandroid.ui.chat

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.ChatData
import kotlinx.android.synthetic.main.item_chat_other.view.*


class OtherUserHolder(view: View) : RecyclerView.ViewHolder(view) {

    val messageText = view.text_gchat_message_other
    val date = view.text_gchat_date_other
    val timestamp = view.text_gchat_timestamp_other
    val profileImage = view.image_gchat_profile_other
    val user = view.text_gchat_user_other


    fun bindView(context: Context, message: ChatData) {

        messageText.text = message.message

//        timestamp.text = DateUtil.formatTime(message.createdAt)
        timestamp.text = "TODO: Date"

        date.visibility = View.VISIBLE
//        date.text = DateUtil.formatDate(message.createdAt)
//        date.text = DateUtil.formatDate(message.createdAt)
        date.text = "TODO: Date"

//        Glide.with(context).load(message.profileUrl).apply(RequestOptions().override(75, 75))
//            .into(profileImage)
        user.text = message.senderId

    }
}
