package com.example.taxishare.app

import android.app.Application
import com.facebook.stetho.Stetho

class AppInitializer : Application() {

    override fun onCreate() {
        super.onCreate()

        // stetho initialize
        Stetho.initializeWithDefaults(this)

    }
}