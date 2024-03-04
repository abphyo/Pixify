package com.biho.pixify.core.domain.usecases

import android.app.Notification
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.app.NotificationCompat
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.biho.pixify.core.host.download.DownloadRepository
import com.biho.pixify.core.model.util.Constants
import com.biho.pixify.core.model.util.DownloadResult
import com.biho.pixify.core.model.util.FileUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import java.io.File

class DownloadWithForegroundService(
    private val context: Context,
    private val downloadRepoImpl: DownloadRepository
) {

    suspend operator fun invoke(postId: Int, fileUrl: String): Flow<Pair<Int, Notification>> {

        val notificationId = downloadRepoImpl.incrementNotificationCounter()
        val file = File(fileUrl)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
        val tempFileUri = File(FileUtils.getTempDir(context), file.name).toUri()
        val contentValue = ContentValues().apply {
            val directory = when {
                mimeType?.contains("video") == true -> Environment.DIRECTORY_MOVIES
                mimeType?.contains("image") == true -> Environment.DIRECTORY_PICTURES
                else -> Environment.DIRECTORY_DOWNLOADS
            }
            put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                directory + File.separator + Constants.APP_NAME
            )
        }
        val contentUri = when {
            mimeType?.contains("video") == true -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            mimeType?.contains("image") == true -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            else -> when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> MediaStore.Downloads.EXTERNAL_CONTENT_URI
                else -> Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toUri()
            }
        }
        val resolver = context.contentResolver
        val fileUri = resolver.insert(contentUri, contentValue)
        val pendingCancelDownloadIntent = PendingIntent.getBroadcast(
            context,
            1,
            Intent(
                // BroadcastReceiver class
            ).apply {
                action = postId.toString()
                putExtra(Constants.NOTIFICATION_ID, notificationId)
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )
        val bootstrapNotification = NotificationCompat.Builder(context, Constants.DOWNLOAD_CHANNEL)
            .setContentTitle("downloading " + file.name)
            .setChannelId(Constants.DOWNLOAD_CHANNEL)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
        return flow {
            downloadRepoImpl.download(
                postId = postId,
                url = fileUrl,
                uri = tempFileUri,
                sample = 1200L
            ).collectLatest { result ->

                when(result) {
                    is DownloadResult.Starting ->
                        Pair(
                            notificationId,
                            bootstrapNotification
                                .setProgress(
                                    100,
                                    0,
                                    true
                                )
                                .setSubText("0% • 0B/s")
                                .setContentText("0B / 0B")
                                .build()
                        )
                    is DownloadResult.Downloading -> {
                        val fmtSize = FileUtils.formatFileSize(bytes = result.size)
                        val fmtDownloadedSize = FileUtils.formatFileSize(bytes = result.downloadedSize)
                        val progressPercentage = (result.progress * 100).toInt()
                        val fmtSpeed = FileUtils.formatFileSize(bytes = result.speed)
                        emit(
                            Pair(
                                notificationId,
                                bootstrapNotification
                                    .setProgress(
                                        100,
                                        progressPercentage,
                                        result.downloadedSize == 0L
                                    )
                                    .setSubText("$progressPercentage • $fmtSpeed/s")
                                    .setContentText("$fmtDownloadedSize / $fmtSize")
                                    .build()
                            )
                        )
                    }
                    is DownloadResult.Completed -> {

                        FileUtils.copyFile(
                            context = context,
                            sourceUri = tempFileUri,
                            destinationUri = fileUri!!
                        )
                        tempFileUri.toFile().delete()
                        val openDownloadedFileIntent = Intent().apply {
                            action = Intent.ACTION_VIEW
                            setDataAndType(fileUri, resolver.getType(fileUri))
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        val pendingOpenDownloadedFileIntent = PendingIntent.getActivity(
                            context,
                            1,
                            openDownloadedFileIntent,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                        emit(
                            Pair(
                                notificationId,
                                bootstrapNotification
                                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                                    .setContentTitle(file.name)
                                    .setSubText(null)
                                    .setContentText("download completed")
                                    .setOngoing(false)
                                    .setAutoCancel(true)
                                    .setOnlyAlertOnce(false)
                                    .setContentIntent(pendingOpenDownloadedFileIntent)
                                    .clearActions()
                                    .build()
                            )
                        )
                    }
                    is DownloadResult.Failed -> {
                        emit(
                            Pair(
                                notificationId,
                                bootstrapNotification
                                    .setSmallIcon(android.R.drawable.stat_notify_error)
                                    .setContentTitle(file.name)
                                    .setSubText(null)
                                    .setContentText("download error ${result.message}")
                                    .setOngoing(false)
                                    .setAutoCancel(true)
                                    .setOnlyAlertOnce(false)
                                    .clearActions()
                                    .build()
                            )
                        )
                    }
                }

            }
        }
    }

}