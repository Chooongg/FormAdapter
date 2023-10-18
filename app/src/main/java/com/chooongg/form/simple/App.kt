package com.chooongg.form.simple

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.utils.manager.NightModeManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NightModeManager.setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        FlipperInitializer.initialize(this)
    }
}