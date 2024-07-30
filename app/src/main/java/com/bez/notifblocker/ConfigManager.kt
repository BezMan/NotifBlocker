package com.bez.notifblocker

import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

object ConfigManager {
    private var config: Config? = null

    fun initialize(context: Context) {
        val inputStream = context.assets.open("config.json")
        val reader = InputStreamReader(inputStream)
        config = Gson().fromJson(reader, Config::class.java)
    }

    fun getConfig(): Config {
        return config ?: throw IllegalStateException("ConfigManager is not initialized")
    }
}
