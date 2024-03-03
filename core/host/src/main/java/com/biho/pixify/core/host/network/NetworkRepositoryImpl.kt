package com.biho.pixify.core.host.network

import android.content.Context
import com.biho.pixify.core.model.util.DomainResult
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.dnsoverhttps.DnsOverHttps
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.HttpException
import retrofit2.Retrofit

class NetworkRepositoryImpl(
    appContext: Context
): NetworkRepository {

    private val isDohEnabled = false

    private val bootstrapOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor(context = appContext))
        .addInterceptor {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY).intercept(it)
        }
        .build()

    private val dns = DnsOverHttps.Builder()
        .client(bootstrapOkHttpClient)
        .url("https://cloudflare-dns.com/dns-query".toHttpUrl())
        .build()

    private val okHttpClientDoh = bootstrapOkHttpClient.newBuilder()
        .dns(dns)
        .build()

    override val okHttpClient: OkHttpClient
        get() = when {
            isDohEnabled -> okHttpClientDoh
            else -> bootstrapOkHttpClient
        }

    private val networkClient = Retrofit.Builder()
        .baseUrl("https://github.com")
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(NetworkApi::class.java)

    override fun getFileSize(url: String): Flow<DomainResult<Long>> {
        return flow {
            try {
                val response = networkClient.getFileSize(url)
                when {
                    response.isSuccessful -> {
                        val fileSize = response.headers()["content-length"]?.toLong() ?: 0L
                        emit(DomainResult.Success(data = fileSize, statusCode = response.code()))
                    }
                    else -> {
                        val errorDto = response.errorBody()?.toErrorDto()
                        emit(DomainResult.Error(message = errorDto?.message ?: "unknown file size"))
                    }
                }
            } catch (e: HttpException) {
                emit(DomainResult.Error(message = "unknown file size"))
            } catch (e: IOException) {
                emit(DomainResult.Error(message = "unknown file size"))
            }
        }
    }

    override fun downloadFile(url: String): ResponseBody {
        return networkClient.downloadFile(url)
    }
}