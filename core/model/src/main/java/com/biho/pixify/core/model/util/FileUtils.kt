package com.biho.pixify.core.model.util

import android.content.Context
import android.net.Uri
import java.io.File
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.roundToLong

object FileUtils {

    fun formatFileSize(bytes: Long): String {
        if (bytes == 0L)
            return "0 B"

        val sizeName = listOf("B", "KB", "MB", "GB")

        val i = floor(ln(bytes.toDouble()) / ln(1024.0))
        val p = 1024.0.pow(i)
        val s = (bytes / p * 100).roundToLong() / 100.0

        return "$s ${sizeName[i.toInt()]}"
    }

    fun getTempDir(context: Context): File {
        return File(context.cacheDir, Constants.TEMP_DIRECTORY).also { dir ->
            if (!dir.isDirectory)
                dir.mkdir()
        }
    }

    fun copyFile(
        context: Context,
        sourceUri: Uri,
        destinationUri: Uri,
    ) {
        val resolver = context.contentResolver
        try {
            resolver.openInputStream(sourceUri)?.use { inputStream ->
                resolver.openOutputStream(destinationUri)?.use { outputStream ->
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var bytes = inputStream.read(buffer)
                    while (bytes >= 0) {
                        outputStream.write(buffer, 0, bytes)
                        bytes = inputStream.read(buffer)
                    }
                }
            }
        } catch (e: Exception) {
            println("error copying downloaded file")
        }
    }

}