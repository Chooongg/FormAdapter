package com.chooongg.formAdapter.simple

import android.app.Application
import android.view.Gravity
import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.formAdapter.FormManager
import com.chooongg.utils.manager.NightModeManager
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NightModeManager.setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        Stetho.initializeWithDefaults(this)
        SoLoader.init(this, false)
        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.start()
        }

        FormManager.contentGravity = Gravity.END
    }
}