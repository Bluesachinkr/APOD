package com.android.apod.utils


import android.os.Environment
import java.io.File

class ApodFileUtils {
    companion object {
        fun getDirectory(): String? {
            val storage = Environment.getExternalStorageDirectory().absolutePath
            val directory = File(storage, "APOD")
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    return null;
                }
            }
            return directory.absolutePath
        }
    }
}