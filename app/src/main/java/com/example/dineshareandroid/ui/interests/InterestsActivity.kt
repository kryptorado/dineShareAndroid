package com.example.dineshareandroid.ui.interests

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Interest
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.loggedIn.LoggedInActivity
import com.example.dineshareandroid.ui.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_interests.*


class InterestsActivity : AppCompatActivity() {
    private val TAG = "InterestsActivity"
    private var adapter = RecyclerAdapter(mutableListOf(), listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        val model: InterestsViewModel by viewModels()
        model.interests.observe(this) { interests ->
            showInterestList(interests)
            Log.d(TAG, "Got interests from viewmodel: $interests")
        }

        model.updateSuccess.observe(this) {isSuccess ->
            if (isSuccess) {
                Toast.makeText(applicationContext, "Updated successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoggedInActivity::class.java))
                finish()

            } else {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        interests_save_button.setOnClickListener {
            for (interest in adapter.interests) {
                model.updateInterests(adapter.interests)
                Log.d(TAG, "New adapter strengths: ${interest.strength}")
            }
        }
    }

    private fun showInterestList(interests: List<Interest>) {
        val recyclerView = findViewById<RecyclerView>(R.id.interests_recyclerview)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val iconList = arrayListOf<Int>()

        // map correct icon to interest because list isn't always in the same order
        for (interest in interests) {
            if (interest.name.equals("music")) {
                iconList.add(R.drawable.ic_baseline_music_note_24)
            } else if ((interest.name.equals("sports"))) {
                iconList.add(R.drawable.ic_baseline_sports_tennis_24)
            } else if ((interest.name.equals("video games"))) {
                iconList.add(R.drawable.ic_baseline_videogame_asset_24)
            } else if ((interest.name.equals("arts & crafts"))) {
                iconList.add(R.drawable.ic_baseline_brush_24)
            } else if ((interest.name.equals("cooking"))) {
                iconList.add(R.drawable.ic_baseline_room_service_24)
            }
        }

        adapter = RecyclerAdapter(interests as MutableList<Interest>, iconList)
        recyclerView.adapter = adapter
    }
}