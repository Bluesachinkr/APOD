package com.android.apod.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class RuntimePermissions(permissions: Array<String>, context: Context) {
    private val context = context
    private val permissions = permissions

    private val REQUEST_CODE = 1

    init {
        if (hasPermissions() == false) {
            ActivityCompat.requestPermissions(context as Activity, permissions, REQUEST_CODE)
        }
    }

    open fun hasPermissions(): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                return false
            }
        }
        return true
    }
}