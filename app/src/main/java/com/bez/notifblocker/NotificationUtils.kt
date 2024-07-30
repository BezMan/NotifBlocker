package com.bez.notifblocker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

object NotificationUtils {

    private const val NOTIF_TITLE = "my title"
    private const val NOTIF_TEXT = "my text"
    private const val CHANNEL_ID = "notification_channel_id"
    private const val NOTIF_ID = 111
    private const val CHANNEL_NAME = "Notification Channel"

    private var notificationManager: NotificationManager? = null

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager?.createNotificationChannel(channel)
    }

    fun postNotification(context: Context) {
        val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(NOTIF_TITLE)
            .setContentText(NOTIF_TEXT)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager?.notify(NOTIF_ID, notification)
    }

}
