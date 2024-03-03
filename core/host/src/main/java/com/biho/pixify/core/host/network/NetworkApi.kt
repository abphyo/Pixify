package com.biho.pixify.core.host.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface NetworkApi {

    @Streaming
    @GET
    fun getFileSize(@Url url: String): Response<Void>

    @Streaming
    fun downloadFile(@Url url: String): ResponseBody

}