package com.biho.pixify.core.host.download

import android.content.Context
import android.net.Uri
import com.biho.pixify.core.host.network.NetworkRepository
import com.biho.pixify.core.model.util.Constants
import com.biho.pixify.core.model.util.DownloadResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DownloadRepositoryImpl(
    context: Context,
    private val networkRepositoryImpl: NetworkRepository
) : DownloadRepository {

    private val downloads = mutableMapOf<Int, Job>()

    private val resolver = context.contentResolver

    override fun incrementNotificationCounter(): Int = downloads.size + 1

    override fun isPostAlreadyDownloading(postId: Int): Boolean = downloads.containsKey(postId)

    override fun add(postId: Int, job: Job) {
        downloads[postId] = job
    }

    override fun remove(postId: Int) {
        downloads[postId]?.cancel()
        downloads.remove(postId)
    }

    override fun download(postId: Int, url: String, uri: Uri, sample: Long): Flow<DownloadResult> {
        return channelFlow {
            var lastDownloadedSize = 0L
            val job = downloadFile(url = url, uri = uri).onEach { downloadResult ->
                when (downloadResult) {
                    is DownloadResult.Starting -> send(downloadResult)
                    is DownloadResult.Downloading -> {
                        send(
                            downloadResult.copy(
                                speed = (downloadResult.downloadedSize - lastDownloadedSize) / Constants.ONE_SECOND
                            )
                        )
                        lastDownloadedSize = downloadResult.downloadedSize
                        delay(timeMillis = sample)
                    }

                    is DownloadResult.Completed, is DownloadResult.Failed -> {
                        remove(postId)
                        send(downloadResult)
                    }
                }
            }.launchIn(CoroutineScope(context = Dispatchers.IO))
            add(postId = postId, job = job)
        }
    }

    private fun downloadFile(url: String, uri: Uri): Flow<DownloadResult> {
        return flow {
            emit(DownloadResult.Starting)
            try {
                val responseBody = networkRepositoryImpl.downloadFile(url = url)
                val size = responseBody.contentLength()
                responseBody.byteStream().use { inputStream ->
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                        var downloadedSize = 0L
                        var bytes = inputStream.read(buffer)
                        while (bytes >= 0) {
                            outputStream.write(buffer, 0, bytes)
                            downloadedSize += bytes
                            bytes = inputStream.read(buffer)
                            emit(
                                DownloadResult.Downloading(
                                    downloadedSize = downloadedSize,
                                    size = size,
                                    progress = downloadedSize.toFloat() / size.toFloat()
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                println("error downloading file")
            }
            networkRepositoryImpl.downloadFile(url)
        }
    }
}