package com.biho.pixify.core.model.util

import kotlinx.coroutines.flow.Flow

object UiResult {
    operator fun <T> invoke(result: DomainResult<T>): Result<T> {
        return when(result) {
            is DomainResult.Success -> Result.success(result.data)
            is DomainResult.Error -> Result.failure(Throwable(result.message))
        }
    }
}