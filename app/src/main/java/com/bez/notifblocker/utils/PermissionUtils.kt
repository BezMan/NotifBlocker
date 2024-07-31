package com.bez.notifblocker.utils

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.view.KeyEvent
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat


object PermissionUtils {

    fun checkAndRequestPermissions(
        context: Context,
        requestPermissionLauncher: ActivityResultLauncher<String>,
        onPermissionGranted: () -> Unit,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                showPermissionRationale(
                    context,
                    "POST_NOTIFICATIONS",
                    "allow POST_NOTIFICATIONS permission to protect yourself from malware."
                ) {
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
            } else {
                checkNotificationListenerPermission(context, onPermissionGranted)
            }
        } else {
            checkNotificationListenerPermission(context, onPermissionGranted)
        }
    }

    private fun checkNotificationListenerPermission(
        context: Context,
        onPermissionGranted: () -> Unit,
    ) {
        if (!isNotificationServiceEnabled(context)) {
            showNotificationListenerRationale(context)
        } else {
            onPermissionGranted()
        }
    }

    private fun isNotificationServiceEnabled(context: Context): Boolean {
        val enabledNotificationListeners = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        val packageName = context.packageName
        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName)
    }

    private fun showPermissionRationale(context: Context, permission: String, message: String, onPositive: () -> Unit) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Permission Required")
            .setMessage(message)
            .setPositiveButton("Allow") { _, _ -> onPositive() }
            .setCancelable(false) // Prevent dismissing by clicking outside or pressing back
            .create()

        dialog.setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK // Disable the back button
        }

        dialog.show()
    }

    private fun showNotificationListenerRationale(context: Context) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Permission Required")
            .setMessage("This app requires access to notifications. Please enable it in the settings for full malware protection.")
            .setPositiveButton("Allow") { _, _ ->
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                context.startActivity(intent)
            }
            .setCancelable(false) // Prevent dismissing by clicking outside or pressing back
            .create()

        dialog.setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK // Disable the back button
        }

        dialog.show()
    }
}
