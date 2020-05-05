package com.materialstudies.owl

import android.app.Application
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q
import androidx.appcompat.app.AppCompatDelegate

class OwlApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val nightMode = if (SDK_INT >= Q) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        } else {
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
