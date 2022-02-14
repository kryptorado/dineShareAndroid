package com.example.dineshareandroid.ui.loggedIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.dineshareandroid.MainActivity
import com.example.dineshareandroid.R
import com.example.dineshareandroid.ui.callLog.CallLogFragment
import com.example.dineshareandroid.ui.conversations.ConversationsFragment
import com.example.dineshareandroid.ui.profile.MatchingFragment
import com.example.dineshareandroid.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class LoggedInActivity : AppCompatActivity() {
    private val TAG = "LoggedInActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logged_in_base)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener(navListener)
        bottomNav.selectedItemId = R.id.nav_profile;
    }

    private val navListener =
        NavigationBarView.OnItemSelectedListener{ item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_profile -> selectedFragment = ProfileFragment()
                R.id.nav_messages -> selectedFragment = ConversationsFragment()
                R.id.nav_call_log -> selectedFragment = CallLogFragment()
                R.id.nav_matching -> selectedFragment = MatchingFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment
                ).commit()
            }
            true
        }


//       Amplify.Auth.fetchUserAttributes(
//            this::onFetchSuccess,
//            this::onError
//        )

//        val authUser = Amplify.Auth.currentUser
//        logged_in_text_username.text = authUser.username
//        logged_in_text_userid.text = authUser.userId
//
//
//        logged_in_button_logout.setOnClickListener {
//            Amplify.Auth.signOut(
//                this::onLogoutSuccess,
//                this::onError
//            )
//        }
//
//        logged_in_button_interests.setOnClickListener {
//            startActivity(Intent(this, InterestsActivity::class.java))
//        }
//
//        logged_in_button_video_call.setOnClickListener{
//            startActivity(Intent(this, VideoChatActivity::class.java))
//        }
//    }

    private fun onFetchSuccess(attrs: List<AuthUserAttribute>) {
        val attrMap = attrs.map { it.key to it.value }.toMap()
        val firstName = attrMap[AuthUserAttributeKey.givenName()]
        val lastName = attrMap[AuthUserAttributeKey.familyName()]
        val email = attrMap[AuthUserAttributeKey.email()]

        // TODO: check if login for first time

        val interests = mutableListOf<Int>()
        interests.addAll(mutableListOf(1, 2, 3)) // TODO: don't hardcode all the interests

        val user = User.builder()
            .id(Amplify.Auth.currentUser.userId)
            .firstName(firstName)
            .lastName(lastName)
            .interests(interests)
            .email(email)
            .build()

        Amplify.API.mutate(
            ModelMutation.create(user),
            { Log.i(TAG, "CREATE user succeeded: $it") },
            { Log.e(TAG, "CREATE user failed", it) }
        )
    }

    private fun onError(error: AuthException) {
        runOnUiThread {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
        }
        Log.e("LoggedInActivity", "auth exception $error")
    }

    private fun onLogoutSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}