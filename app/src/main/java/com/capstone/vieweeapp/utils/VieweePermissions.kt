package com.capstone.vieweeapp.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object VieweePermissions {

    val REQUIRED_PERMISSIONS =
        mutableListOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) { // version check (optional)
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

    const val REQUEST_CODE = 1000

    fun hasPermissions(context: Context) = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}