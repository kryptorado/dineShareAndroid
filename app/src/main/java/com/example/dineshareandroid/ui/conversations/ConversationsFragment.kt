package com.example.dineshareandroid.ui.conversations


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.ChatRoom
import com.example.dineshareandroid.R
import com.example.dineshareandroid.utils.LoadingDialog
import kotlinx.android.synthetic.main.fragment_messages.*


class ConversationsFragment : Fragment(R.layout.fragment_messages) {
    private val TAG = "ConversationsFragment"
    private val model: ConversationsViewModel by viewModels()
    private var adapter = ConversationsRecyclerAdapter(mutableListOf(), object : ConversationsRecyclerAdapter.ItemClickListener {
        override fun onItemClick(position: Int) {
//            TODO("Not yet implemented")
        }
    })

    lateinit var loader: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.chatRooms.observe(viewLifecycleOwner) { chatRooms ->
            if (chatRooms.isEmpty()) {
                conversation_is_empty.visibility = View.VISIBLE
                messages_button_deleteall.visibility = View.GONE
            } else {
                conversation_is_empty.visibility = View.GONE
                messages_button_deleteall.visibility = View.VISIBLE
            }

            showChatRooms(ArrayList<ChatRoom>(chatRooms))
            Log.d(TAG, "got chatRooms: $chatRooms")
        }

        messages_button_deleteall.setOnClickListener {
            for (chatRoom in adapter.chatRooms) {
                model.deleteChatRoom(chatRoom)
            }
            adapter.clearAll()
            conversation_is_empty.visibility = View.VISIBLE
            messages_button_deleteall.visibility = View.GONE
        }
    }

    private fun showChatRooms(chatRooms: List<ChatRoom>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.chatrooms_recyclerview)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = layoutManager
        adapter = ConversationsRecyclerAdapter(chatRooms as MutableList<ChatRoom>, object : ConversationsRecyclerAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                // this function will handle the item click on a provided position
                model.deleteChatRoom(adapter.chatRooms[position])
                if (adapter.chatRooms.isEmpty()) {
                    conversation_is_empty.visibility = View.VISIBLE
                    messages_button_deleteall.visibility = View.GONE

                } else {
                    conversation_is_empty.visibility = View.GONE
                    messages_button_deleteall.visibility = View.VISIBLE
                }
            }
        })
        recyclerView?.adapter = adapter
    }
}