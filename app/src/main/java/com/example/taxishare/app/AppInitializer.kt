package com.example.taxishare.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.google.firebase.messaging.FirebaseMessaging

class AppInitializer : Application() {

    override fun onCreate() {
        super.onCreate()

        // stetho initialize
        Stetho.initializeWithDefaults(this)

        // FCM 유지
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

    }
}