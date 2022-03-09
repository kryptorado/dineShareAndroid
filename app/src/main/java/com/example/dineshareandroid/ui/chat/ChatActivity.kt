package com.example.dineshareandroid.ui.chat

import android.os.Build
import android.os.Bundle
import android.service.carrier.CarrierMessagingService
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dineshareandroid.AmplifyApp
import com.example.dineshareandroid.R
import io.agora.rtm.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"
    private var mRtmChannel: RtmChannel? = null

    override fun onDestroy() {
        super.onDestroy()
        mRtmChannel?.leave(null)
        mRtmChannel?.release()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val app: AmplifyApp = applicationContext as AmplifyApp
        val mRtmClient = app.mRtmClient

        joinChatChannel(mRtmClient!!)
        button_gchat_send.setOnClickListener {
            onClickSendChannelMsg(mRtmClient)
        }
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
                val text = """Message sent to channel ${
                    mRtmChannel?.getId().toString()
                } : ${message?.getText().toString()}
    """
                writeToMessageHistory(text)
            }

            override fun onFailure(errorInfo: ErrorInfo) {
                val text = """Message fails to send to channel ${
                    mRtmChannel!!.getId().toString()
                } Error: ${errorInfo.toString()}
    """
                writeToMessageHistory(text)
            }

            override fun onReceiveResult(p0: Void) {
                TODO("Not yet implemented")
            }
        })
    }


    fun writeToMessageHistory(record: String?) {
        chat_text_history?.append(record)
    }


    private fun getChannelName(): String {
        var channelName = ""
        if (intent.hasExtra("channelName")) {
            channelName = intent.getStringExtra("channelName").toString()
        }
        return channelName
    }



    private fun joinChatChannel(mRtmClient: RtmClient) {
        val channelName = getChannelName()

        // Create a channel listener
        val mRtmChannelListener: RtmChannelListener = object : RtmChannelListener {
            override fun onMemberCountUpdated(i: Int) {}
            override fun onAttributesUpdated(list: List<RtmChannelAttribute?>?) {}
            override fun onMessageReceived(message: RtmMessage, fromMember: RtmChannelMember) {
                val text: String = message.getText()
                val fromUser: String = fromMember.getUserId()
                val message_text = "Message received from other user : $text\n"
                Log.d(TAG, message_text)
                writeToMessageHistory(message_text)

                runOnUiThread {
                    val toast = Toast.makeText(applicationContext, "$message_text", Toast.LENGTH_SHORT)
                    toast.show()
                }
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