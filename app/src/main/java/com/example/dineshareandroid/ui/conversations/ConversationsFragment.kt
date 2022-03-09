package com.example.dineshareandroid.ui.conversations


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.ChatRoom
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.loggedIn.LoggedInActivity
import com.example.dineshareandroid.utils.LoadingDialog


class ConversationsFragment : Fragment(R.layout.fragment_conversations) {
    private val TAG = "ConversationsFragment"
    private val model: ConversationsViewModel by activityViewModels()
    private var adapter = ConversationsRecyclerAdapter(mutableListOf())
    lateinit var loader: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loggedInActivity = activity as LoggedInActivity
        val rtmClient = loggedInActivity.mRtmClient

        model.chatRooms.observe(this) { chatRooms ->
            showChatRooms(chatRooms)
            Log.d(TAG, "got chatRooms: $chatRooms")
        }
    }

    private fun showChatRooms(chatRooms: List<ChatRoom>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.chatrooms_recyclerview)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = layoutManager
        adapter = ConversationsRecyclerAdapter(chatRooms as MutableList<ChatRoom>)
        recyclerView?.adapter = adapter
    }
}