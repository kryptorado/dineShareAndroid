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
import com.example.dineshareandroid.ui.matching.MatchingFragment
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
}