package com.example.dineshareandroid.ui.messageList

import androidx.recyclerview.widget.RecyclerView
import android.R

import android.widget.TextView

import android.view.LayoutInflater

import android.view.ViewGroup


/*class MessageListAdapter(context: Context, messageList: List<BaseMessage>) :
    RecyclerView.Adapter<Any?>() {
    private val mContext: Context
    private val mMessageList: List<BaseMessage>
    override fun getItemCount(): Int {
        return mMessageList.size
    }

    // Determines the appropriate ViewType according to the sender of the message.
    override fun getItemViewType(position: Int): Int {
        val message: UserMessage = mMessageList[position] as UserMessage
        return if (message.getSender().getUserId().equals(SendBird.getCurrentUser().getUserId())) {
            // If the current user is the sender of the message
            VIEW_TYPE_MESSAGE_SENT
        } else {
            // If some other user sent the message
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Any {
        val view: View
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sent, parent, false)
            return SentMessageHolder(view)
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_received, parent, false)
            return ReceivedMessageHolder(view)
        }
        return null
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val message: UserMessage = mMessageList[position] as UserMessage
        when (holder!!.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder?)!!.bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder?)!!.bind(message)
        }
    }

    private inner class SentMessageHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView
        fun bind(message: UserMessage) {
            messageText.setText(message.getMessage())

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
        }

        init {
            messageText = itemView.findViewById(R.id.text_message_body)
            timeText = itemView.findViewById(R.id.text_message_time)
        }
    }

    private inner class ReceivedMessageHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView
        var nameText: TextView
        var profileImage: ImageView
        fun bind(message: UserMessage) {
            messageText.setText(message.getMessage())

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
            nameText.setText(message.getSender().getNickname())

            // Insert the profile image from the URL into the ImageView.
            Utils.displayRoundImageFromUrl(
                mContext,
                message.getSender().getProfileUrl(),
                profileImage
            )
        }

        init {
            messageText = itemView.findViewById(R.id.text_message_body)
            timeText = itemView.findViewById(R.id.text_message_time)
            nameText = itemView.findViewById(R.id.text_message_name)
            profileImage = itemView.findViewById(R.id.image_message_profile) as ImageView
        }
    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }

    init {
        mContext = context
        mMessageList = messageList
    }
}*/