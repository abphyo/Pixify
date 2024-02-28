package com.biho.pixify.core.model.util

sealed interface DomainResult<T> {
    data class Success<T> (val data: T, val statusCode: Int) : DomainResult<T>
    data class Error<T> (val message: String) : DomainResult<T>
}

suspend inline fun <reified A, reified B> DomainResult<A>.mapSuccess(
    crossinline businessBlock: suspend (A, code: Int) -> B
): Result<B> {
    return when(this) {
        is DomainResult.Success -> Result.success(businessBlock(data, statusCode))
        is DomainResult.Error -> Result.failure(Throwable(message))
    }
}

