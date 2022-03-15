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
import com.example.dineshareandroid.utils.LoadingDialog
import kotlinx.android.synthetic.main.activity_interests.*


class InterestsActivity : AppCompatActivity() {
    private val TAG = "InterestsActivity"
    private var adapter = InterestsRecyclerAdapter(mutableListOf(), listOf())
    lateinit var loader: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)
        loader = LoadingDialog(this)

        val model: InterestsViewModel by viewModels()
        model.interests.observe(this) { interests ->
            showInterestList(interests)
            Log.d(TAG, "Got interests from viewmodel: $interests")
        }

        model.updateSuccess.observe(this) {isSuccess ->
            if (isSuccess) {
//                loader.dismiss()
                Toast.makeText(applicationContext, "Updated successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoggedInActivity::class.java))
                finish()

            } else {
//                loader.dismiss()
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        interests_save_button.setOnClickListener {
//            loader.show()
            model.updateInterests(adapter.interests)
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
            } else if ((interest.name.equals("astrology"))) {
                iconList.add(R.drawable.ic_baseline_star_rate_24)
            } else if ((interest.name.equals("business"))) {
                iconList.add(R.drawable.ic_baseline_attach_money_24)
            } else if ((interest.name.equals("science"))) {
                iconList.add(R.drawable.ic_baseline_science_24)
            } else if ((interest.name.equals("food"))) {
                iconList.add(R.drawable.ic_baseline_dinner_dining_24)
            } else if ((interest.name.equals("nature"))) {
                iconList.add(R.drawable.ic_baseline_grass_24)
            } else if ((interest.name.equals("travelling"))) {
                iconList.add(R.drawable.ic_baseline_flight_takeoff_24)
            } else if ((interest.name.equals("history"))) {
                iconList.add(R.drawable.ic_baseline_auto_stories_24)
            } else if ((interest.name.equals("architecture"))) {
                iconList.add(R.drawable.ic_baseline_architecture_24)
            } else if ((interest.name.equals("fashion"))) {
                iconList.add(R.drawable.ic_baseline_checkroom_24)
            } else if ((interest.name.equals("shopping"))) {
                iconList.add(R.drawable.ic_baseline_local_mall_24)
            } else if ((interest.name.equals("TV shows"))) {
                iconList.add(R.drawable.ic_baseline_tv_24)
            } else if ((interest.name.equals("languages"))) {
                iconList.add(R.drawable.ic_baseline_translate_24)
            } else if ((interest.name.equals("weather"))) {
                iconList.add(R.drawable.ic_baseline_umbrella_24)
            } else if ((interest.name.equals("politics"))) {
                iconList.add(R.drawable.ic_baseline_handshake_24)
            } else if ((interest.name.equals("medicine"))) {
                iconList.add(R.drawable.ic_baseline_local_hospital_24)
            }
        }

        adapter = InterestsRecyclerAdapter(interests as MutableList<Interest>, iconList)
        recyclerView.adapter = adapter
    }
}

//val interestNames = listOf<String>("sports", "music", "cooking", "video games", "arts & crafts", "astrology", "business", "science", "food", "nature", "travelling", "history", "architecture", "fashion", "shopping", "TV shows", "languages", "weather", "politics", "medicine")
