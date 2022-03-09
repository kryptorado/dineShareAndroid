package com.example.dineshareandroid.ui.conversations


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.CallLog
import com.amplifyframework.datastore.generated.model.ChatRoom
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.LoadingDialog


class ConversationsFragment : Fragment(R.layout.fragment_conversations) {
    private val TAG = "ConversationsFragment"
    private val model: ConversationsViewModel by viewModels()
    private var adapter = ConversationsRecyclerAdapter(mutableListOf())
    lateinit var loader: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.chatRooms.observe(viewLifecycleOwner) { chatRooms ->
            showChatRooms(ArrayList<ChatRoom>(chatRooms))
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