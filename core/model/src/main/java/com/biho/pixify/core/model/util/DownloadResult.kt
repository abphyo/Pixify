package com.biho.pixify.core.model.util

sealed class DownloadResult {
    data object Starting: DownloadResult()
    data class Downloading(
        val downloadedSize: Long = 0L,
        val size: Long = 0L,
        val progress: Float = 0f,
        val speed: Long = 0L,
    ): DownloadResult()
    data class Completed(val size: Long): DownloadResult()
    data class Failed(val message: String): DownloadResult()
}