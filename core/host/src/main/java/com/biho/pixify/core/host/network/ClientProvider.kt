package com.biho.pixify.core.host.network

import android.content.Context
import android.util.Log
import com.biho.pixify.core.host.danbooru.HostApiService
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

fun provideAuthClient(
    hostType: HostType,
    okHttpClient: OkHttpClient
): HostApiService {

    return Retrofit.Builder()
        .baseUrl(hostType.getBaseUrl())
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(HostApiService::class.java)

}

fun provideClient(appContext: Context, hostType: HostType): HostApiService {

    val checkerInterceptor = provideChuckerInterceptor(appContext)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(checkerInterceptor)
        .build()
    return Retrofit.Builder()
        .baseUrl(hostType.getBaseUrl())
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(HostApiService::class.java)

}

fun provideChuckerInterceptor(appContext: Context): ChuckerInterceptor {

    val chuckerCollector = ChuckerCollector(
        context = appContext,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )
    return ChuckerInterceptor.Builder(appContext)
        .collector(chuckerCollector)
        .maxContentLength(250_000L)
        .redactHeaders("Auth-Token", "Bearer")
        .alwaysReadResponseBody(true)
        .createShortcut(true)
        .build()

}

class AuthInterceptor(name: String, apiKey: String) : Interceptor {

    private val credentials = Credentials.basic(username = name, password = apiKey)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(name = "Authorization", value = credentials)
            .build()
        Log.d("request_url", "intercept: ${request.url}")
        return chain.proceed(newRequest)
    }

}