package com.chooongg.form.simple

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FlipperInitializer.initialize(this)
    }
}