package com.biho.pixify.core.host.network

import com.biho.pixify.core.host.danbooru.dtos.error.PostErrorDto
import com.biho.pixify.core.model.danbooru.repository.ImageBoardRepository
import com.biho.pixify.core.model.util.DomainResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class ApiCall : ImageBoardRepository {

    suspend inline fun <A, B> toDomain(
        crossinline apiCall: suspend () -> Response<A>,
        crossinline mapper: (A) -> B
    ): DomainResult<B> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<A> = apiCall()
                if (response.isSuccessful) {
                    DomainResult.Success(
                        data = mapper(response.body()!!),
                        statusCode = response.code()
                    )
                } else {
                    val errorDto = response.errorBody()?.toErrorDto()
                    DomainResult.Error(message = errorDto?.message ?: "Unexpected error occurred!")
                }
            } catch (e: HttpException) {
                DomainResult.Error(message = e.localizedMessage ?: "Unexpected error occurred!")
            } catch (e: IOException) {
                DomainResult.Error(message = "Couldn't connect to network!")
            }
        }
    }

    suspend inline fun <A, B> fromDomain(
        crossinline apiCall: suspend (A) -> Response<B>,
        crossinline mapper: () -> A
    ): DomainResult<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall(mapper())
                if (response.isSuccessful) {
                    DomainResult.Success(
                        data = Unit,
                        statusCode = response.code()
                    )
                } else {
                    val errorDto = response.errorBody()?.toErrorDto()
                    DomainResult.Error(message = errorDto?.message ?: "Unexpected error occurred!")
                }
            } catch (e: HttpException) {
                DomainResult.Error(message = e.localizedMessage ?: "Unexpected error occurred!")
            } catch (e: IOException) {
                DomainResult.Error(message = "Couldn't connect to network!")
            }
        }
    }

    fun ResponseBody.toErrorDto(): PostErrorDto? {
        return try {
            Json.decodeFromString(this.toString().trimIndent())
        } catch (e: Exception) {
            println("decoded error response: ${e.message}")
            null
        }
    }

}