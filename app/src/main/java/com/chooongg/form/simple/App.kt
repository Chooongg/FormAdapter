package com.chooongg.form.simple

import android.app.Application
import com.chooongg.form.core.FormManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FlipperInitializer.initialize(this)
        FormManager.defaultConfig {

        }
    }
}