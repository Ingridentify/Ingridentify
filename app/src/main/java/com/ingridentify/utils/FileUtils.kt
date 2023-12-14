package com.ingridentify.utils

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtils {
    fun createCustomTempFile(context: Context): File = File.createTempFile(
        timeStamp,
        ".jpg",
        context.externalCacheDir
    )

    private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    private val timeStamp: String
        get() = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
}