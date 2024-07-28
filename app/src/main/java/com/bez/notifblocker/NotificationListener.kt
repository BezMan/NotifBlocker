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

        // Log the notification
        Log.d("ActiveTask", "timestamp: $timestamp, package: $packageName,  title: $title: text: $text")

        // Check if the package is in the blocked list
        if (packageName in blockedPackages) {
            // Dismiss the notification
            cancelNotification(sbn.key)
        }
    }
}
