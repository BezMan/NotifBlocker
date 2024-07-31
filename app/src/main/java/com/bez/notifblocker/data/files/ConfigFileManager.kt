package com.bez.notifblocker.data.files

import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

object ConfigFileManager {
    private var configFile: ConfigFile? = null

    fun initialize(context: Context) {
        val inputStream = context.assets.open("config.json")
        val reader = InputStreamReader(inputStream)
        configFile = Gson().fromJson(reader, ConfigFile::class.java)
    }

    fun getConfig(): ConfigFile {
        return configFile ?: throw IllegalStateException("ConfigManager is not initialized")
    }
}
