package com.bez.notifblocker

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListener : NotificationListenerService() {

    private val blockedPackages = listOf("com.bez.notifblocker")

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
//        val decryptedLogData = EncryptionUtils.decrypt(encryptedLogData)
//        Log.d("ActiveTask", "Decrypted: $decryptedLogData")

        // Check if the package is in the blocked list
        if (packageName in blockedPackages) {
            // Dismiss the notification
            cancelNotification(sbn.key)
        }
    }
}
