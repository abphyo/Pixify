package com.biho.pixify.core.host.download

import android.net.Uri
import com.biho.pixify.core.model.util.Constants
import com.biho.pixify.core.model.util.DownloadResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface DownloadRepository {

    fun incrementNotificationCounter(): Int

    fun isPostAlreadyDownloading(postId: Int): Boolean

    fun add(postId: Int, job: Job)

    fun remove(postId: Int)

    fun download(postId: Int, url: String, uri: Uri, sample: Long = Constants.ONE_SECOND): Flow<DownloadResult>

}