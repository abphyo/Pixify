package com.biho.pixify.core.host.danbooru.dtos.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostErrorDto(
    @SerialName("backtrace")
    val backtrace: List<String>?,
    @SerialName("error")
    val error: String?,
    @SerialName("message")
    val message: String?,
    @SerialName("success")
    val success: Boolean?
)