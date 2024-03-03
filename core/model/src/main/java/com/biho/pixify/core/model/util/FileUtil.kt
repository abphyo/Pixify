package com.biho.pixify.core.model.util

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtil {

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
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1)
                        outputStream.write(buffer, 0, read)
                }
            }
        } catch (e: Exception) {
            println("error copying downloaded file")
        }
    }

}