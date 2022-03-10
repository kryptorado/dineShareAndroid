package com.example.dineshareandroid.ui.chat

import android.os.Build
import android.os.Bundle
import android.service.carrier.CarrierMessagingService
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.CallLog
import com.amplifyframework.datastore.generated.model.ChatData
import com.example.dineshareandroid.AmplifyApp
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.callLog.CallLogViewModel
import io.agora.rtm.*
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"
    private var mRtmChannel: RtmChannel? = null
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MessageAdapter

    private val model: ChatViewModel by viewModels()



    override fun onDestroy() {
        super.onDestroy()
        mRtmChannel?.leave(null)
        mRtmChannel?.release()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setUpRecyclerView()
        getMessages(adapter)

        val app: AmplifyApp = applicationContext as AmplifyApp
        val mRtmClient = app.mRtmClient

        joinChatChannel(mRtmClient!!)

        button_gchat_send.setOnClickListener {
            onClickSendChannelMsg(mRtmClient)
        }

        // set other user name on top
        toolbar_gchannel.title = getOtherUserName()
    }


    private fun getMessages(adapter: MessageAdapter) {
//        val adapter = MessageAdapter(this)
        val exampleChat1 = ChatData.builder()
            .message("omg why this message")
            .chatRoomId("123444")
            .senderId("3445555")
            .build()

        val exampleChat2 = ChatData.builder()
            .message("lopoooollll")
            .chatRoomId("123444")
            .senderId("4233455")
            .build()
        adapter.loadMessages(mutableListOf(exampleChat1, exampleChat2))

//        model.getChatData(getChannelName())
//        model.chatDataList.observe(this) { chatDataList ->
//            adapter.loadMessages(chatDataList)
//        }


//        recyclerView.scrollToPosition(0)
//        val previousMessageListQuery = groupChannel.createPreviousMessageListQuery()
//
//        previousMessageListQuery.load(
//            100,
//            true,
//            object : PreviousMessageListQuery.MessageListQueryResult {
//                override fun onResult(
//                    messages: MutableList<BaseMessage>?,
//                    e: SendBirdException?
//                ) {
//                    if (e != null) {
//                        Log.e("Error", e.message)
//                    }
//                    adapter.loadMessages(messages!!)
//                }
//            })

    }



    private fun setUpRecyclerView() {

        adapter = MessageAdapter(this)
        recyclerView = recycler_gchat
        recyclerView.adapter = adapter
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        recyclerView.layoutManager = layoutManager
        recyclerView.smoothScrollToPosition(0)

        // Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                recyclerView.smoothScrollToPosition(0)
            }
        })


    }


    // Button to send channel message
    fun onClickSendChannelMsg(mRtmClient: RtmClient) {
        val messageContainer = edit_gchat_message
        val messageContent = messageContainer?.text.toString()
        messageContainer.text.clear()

        val message: RtmMessage? = mRtmClient?.createMessage()
        message?.text = messageContent

        // Send message to channel
        mRtmChannel?.sendMessage(message, @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        object : CarrierMessagingService.ResultCallback<Void?>, io.agora.rtm.ResultCallback<Void> {
            override fun onSuccess(aVoid: Void?) {
                val chatData = ChatData.builder()
                    .message(message?.text.toString())
                    .chatRoomId(getChannelName())
                    .senderId(Amplify.Auth.currentUser.userId)
                    .build()
                adapter.addFirst(chatData)
                recyclerView.smoothScrollToPosition(0)
                model.createChatData(chatData)
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                val text = """Message fails to send to channel ${
                    mRtmChannel!!.getId().toString()
                } Error: ${errorInfo.toString()}
    """
                Log.e(TAG, "FAIL: $text")
            }

            override fun onReceiveResult(p0: Void) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun getChannelName(): String {
        var channelName = ""
        if (intent.hasExtra("channelName")) {
            channelName = intent.getStringExtra("channelName").toString()
        }
        return channelName
    }

    private fun getOtherUserName(): String {
        var otherUserName = ""
        if (intent.hasExtra("otherUserName")) {
            otherUserName = intent.getStringExtra("otherUserName").toString()
        }
        return otherUserName
    }


    private fun joinChatChannel(mRtmClient: RtmClient) {
        val channelName = getChannelName()

        // Create a channel listener
        val mRtmChannelListener: RtmChannelListener = object : RtmChannelListener {
            override fun onMemberCountUpdated(i: Int) {}
            override fun onAttributesUpdated(list: List<RtmChannelAttribute?>?) {}
            override fun onMessageReceived(message: RtmMessage, fromMember: RtmChannelMember) {
                val fromUser: String = fromMember.getUserId()
                val chatData = ChatData.builder()
                    .message(message.text.toString())
                    .chatRoomId(getChannelName())
                    .senderId(fromUser)
                    .build()
                adapter.addFirst(chatData)
                recyclerView.smoothScrollToPosition(0)
            }

            override fun onImageMessageReceived(
                rtmImageMessage: RtmImageMessage?,
                rtmChannelMember: RtmChannelMember?
            ) {
            }

            override fun onFileMessageReceived(
                rtmFileMessage: RtmFileMessage?,
                rtmChannelMember: RtmChannelMember?
            ) {
            }

            override fun onMemberJoined(member: RtmChannelMember?) {
                runOnUiThread {
                    val toast = Toast.makeText(applicationContext, "member $member joined!", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            override fun onMemberLeft(member: RtmChannelMember?) {
                runOnUiThread {
                    val toast = Toast.makeText(applicationContext, "member $member left!", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
        try {
            // Create an RTM channel
            mRtmChannel = mRtmClient?.createChannel(channelName, mRtmChannelListener)
        } catch (e: RuntimeException) {
        }
        // Join the RTM channel
        mRtmChannel?.join(@RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        object : CarrierMessagingService.ResultCallback<Void?>, io.agora.rtm.ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                runOnUiThread {
                    val toast = Toast.makeText(applicationContext, "joined channel $channelName", Toast.LENGTH_LONG)
                    toast.show()
                }

            }
            override fun onFailure(errorInfo: ErrorInfo) {
                val text: CharSequence =
                    "User: " + "lol" + " failed to join the channel!" + errorInfo.toString()
                val duration = Toast.LENGTH_SHORT
                runOnUiThread {
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
            }

            override fun onReceiveResult(p0: Void) {
                TODO("Not yet implemented")
            }
        })
    }
}