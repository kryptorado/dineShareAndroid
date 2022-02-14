package com.example.dineshareandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.ui.loggedIn.LoggedInActivity
import kotlinx.android.synthetic.main.activity_interests.*


class InterestsActivity : AppCompatActivity() {
    private val TAG = "InterestsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        // TODO: fix code..

        Amplify.API.query(
            "dineshareandroid",
            ModelQuery.get(User::class.java, Amplify.Auth.currentUser.userId),
            { user ->
                val interests = (user.data as User).interests
                runOnUiThread {
                    if (interests != null) {
                        if (interests.contains(1)) {
                            interests_checkbox_1.isChecked = true
                        }

                        if (interests.contains(2)) {
                            interests_checkbox_2.isChecked = true
                        }

                        if (interests.contains(3)) {
                            interests_checkbox_3.isChecked = true
                        }
                    }
                }
                Log.i(TAG, "Query results = ${(user.data as User).interests}")
            },
            { Log.e(TAG, "Query failed", it) }
        )


        val list = mutableListOf<Int>()
        interests_checkbox_1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                list.add(1)
            } else {
                list.remove(1)
            }
        }

        interests_checkbox_2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                list.add(2)
            } else {
                list.remove(2)
            }
        }

        interests_checkbox_3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                list.add(3)
            } else {
                list.remove(3)
            }
        }

        interests_save_button.setOnClickListener {
//            val user = User.builder()
//                .id(Amplify.Auth.currentUser.userId)
//                .interests(list)
//                .build()
//
//            Amplify.API.mutate(
//                ModelMutation.update(user),
//                { Log.i(TAG, "UPDATE succeeded: $it") },
//                { Log.e(TAG, "UPDATE failed", it) }
//            )

            startActivity(Intent(this, LoggedInActivity::class.java))
        }


    }
}