package com.bez.notifblocker

import android.app.Application

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        ConfigManager.initialize(this)
        NotificationUtils.initialize(this)

    }
}