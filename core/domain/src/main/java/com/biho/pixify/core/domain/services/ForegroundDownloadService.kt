package com.biho.pixify.core.domain.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.biho.pixify.core.domain.usecases.DownloadWithForegroundService
import com.biho.pixify.core.model.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ForegroundDownloadService(
    private val ioScope: CoroutineScope,
    private val downloadWithForegroundService: DownloadWithForegroundService
) : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            DownloadActions.Start.toString() -> {
                val postId = intent.getIntExtra(Constants.POST_ID, 0)
                val fileUrl = intent.getStringExtra(Constants.FILE_URL)!!
                start(postId, fileUrl)
            }
            DownloadActions.Stop.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    enum class DownloadActions {
        Start,
        Stop
    }

    private fun start(
        postId: Int, fileUrl: String
    ) {
        ioScope.launch {
            downloadWithForegroundService.invoke(postId = postId, fileUrl = fileUrl)
                .collectLatest { pair ->
                    startForeground(pair.first, pair.second)
                }
        }
    }
}