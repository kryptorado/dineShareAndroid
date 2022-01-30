package com.example.dineshareandroid

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify

class AmplifyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify")

            Amplify.Auth.fetchAuthSession(
                { Log.i("AmplifyQuickstart", "Auth session = $it") },
               { Log.e("AmplifyQuickstart", "Failed to fetch auth session", it) }
            )
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }
}