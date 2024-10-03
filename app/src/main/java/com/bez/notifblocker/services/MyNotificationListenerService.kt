package com.bez.notifblocker.services

import android.os.Handler
import android.os.Looper
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.bez.notifblocker.data.files.ConfigFileManager
import com.bez.notifblocker.data.network.RetrofitInstance
import com.bez.notifblocker.utils.EncryptionUtils
import com.bez.notifblocker.utils.NotificationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyNotificationListenerService : NotificationListenerService() {

    private val myPackages = listOf("com.bez.notifblocker")
    private val blockedPackages = mutableListOf<String>()

    private lateinit var handler: Handler
    private lateinit var periodicRunnable: Runnable

    private val runnable = object : Runnable {
        override fun run() {
            NotificationUtils.postNotification(applicationContext)
            handler.postDelayed(this, 5000)  // repeat every 5 seconds
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupPeriodicNotification() //for testing purposes
        fetchAndUpdatePackagesList()
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }


    private fun setupPeriodicNotification() {
        handler = Handler(Looper.getMainLooper())
        periodicRunnable = runnable
        handler.post(periodicRunnable)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val title = sbn.notification.extras.getString("android.title", "No title")
        val text = sbn.notification.extras.getString("android.text", "No text")

        val timestamp = System.currentTimeMillis()

        // Encrypt the log data
        val logData = "timestamp: $timestamp, package: $packageName, title: $title, text: $text"
        val encryptedLogData = EncryptionUtils.encrypt(logData)

        // Log the encrypted data
        Log.d("ActiveTask", encryptedLogData)

        // Decrypt the log data for testing purposes
        val decryptedLogData = EncryptionUtils.decrypt(encryptedLogData)
        Log.d("ActiveTask", "Decrypted: $decryptedLogData")

        // Check if the package is in the blocked list
        if (packageName in blockedPackages) {
            // Dismiss the notification
            cancelNotification(sbn.key)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // Handle notification removal if necessary
    }

    private fun fetchAndUpdatePackagesList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = ConfigFileManager.getConfig().apiEndpoint
                val response = RetrofitInstance.api.fetchData(url)
                if (response.isSuccessful) {
                    val configResponse = response.body()
                    configResponse?.record?.let {
                        blockedPackages.clear()
                        blockedPackages.addAll(it + myPackages)
                        Log.d("NetworkCall", "Updated blocked packages: $blockedPackages")
                    }
                } else {
                    Log.e("NetworkCall", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("NetworkCall", "Exception: ${e.message}")
            }
        }
    }

}
