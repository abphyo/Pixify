package com.biho.pixify.core.host.network

import com.biho.pixify.core.model.util.DomainResult
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

interface NetworkRepository {
    val okHttpClient: OkHttpClient
    fun getFileSize(url: String): Flow<DomainResult<Long>>
    fun downloadFile(url: String): ResponseBody
}