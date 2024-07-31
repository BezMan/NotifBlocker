package com.bez.notifblocker


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bez.notifblocker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the permission launcher
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                checkPermissions()
            } else {
                showToast("POST_NOTIFICATIONS permission is required for this app to function.")
            }
        }

        // Hide the button initially
        binding.permsText.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun checkPermissions() {
        PermissionUtils.checkAndRequestPermissions(
            this,
            requestPermissionLauncher,
            ::initUI
        )
    }

    private fun initUI() {
        // Show the button if permissions are granted
        binding.permsText.visibility = View.GONE
        NotificationUtils.createNotificationChannel(this)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
