package com.bez.notifblocker

import android.app.Application
import com.bez.notifblocker.data.files.ConfigFileManager
import com.bez.notifblocker.utils.NotificationUtils

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        ConfigFileManager.initialize(this)
        NotificationUtils.initialize(this)

    }
}