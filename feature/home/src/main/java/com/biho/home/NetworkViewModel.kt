package com.biho.home

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biho.pixify.core.domain.services.ForegroundDownloadService
import com.biho.pixify.core.domain.usecases.DownloadWithForegroundService
import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.pixify.core.model.danbooru.model.post.Type
import com.biho.pixify.core.model.util.Constants
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class NetworkViewModel(
    context: Context,
    private val downloadWithForegroundService: DownloadWithForegroundService
) : ViewModel() {

    init {
        viewModelScope.launch {
            downloadWithForegroundService.downloadStatusChannel.consumeEach { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPostAlreadyAdded(postId: Int) = downloadWithForegroundService.isPostAlreadyAdded(postId)

    fun downloadPost(post: Post, context: Context) {
        if (isPostAlreadyAdded(postId = post.id)) {
            viewModelScope.launch {
                downloadWithForegroundService
                    .downloadStatusChannel
                    .send(Constants.ADDED)
            }
            return
        }
        when (post.type) {
            Type.ARCHIVE -> {
                when {
                    !post.medias.scaled?.url.isNullOrEmpty() -> {
                        startDownloadService(
                            postId = post.id,
                            fileUrl = post.medias.scaled?.url!!,
                            context = context
                        )
                    }

                    else -> viewModelScope.launch {
                        downloadWithForegroundService
                            .downloadStatusChannel
                            .send(Constants.CANCELED)
                    }
                }
            }

            else -> {
                when {
                    !post.medias.original?.url.isNullOrEmpty() -> {
                        startDownloadService(
                            postId = post.id,
                            fileUrl = post.medias.original?.url!!,
                            context = context
                        )
                    }

                    else -> viewModelScope.launch {
                        downloadWithForegroundService
                            .downloadStatusChannel
                            .send(Constants.CANCELED)
                    }
                }
            }
        }
    }

    private fun startDownloadService(
        postId: Int, fileUrl: String, context: Context
    ) {
        Intent(context, ForegroundDownloadService::class.java).apply {
            action = ForegroundDownloadService.DownloadActions.Start.toString()
            putExtra(Constants.POST_ID, postId)
            putExtra(Constants.FILE_URL, fileUrl)
            context.startService(this)
        }
    }
}