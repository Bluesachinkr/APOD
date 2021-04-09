package com.android.apod.utils

import android.content.Context
import java.io.File

class ApodFileUtils {
    companion object {
        fun getDirectory(context: Context): String? {

            val storage = context.getExternalFilesDir(null)?.absolutePath
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