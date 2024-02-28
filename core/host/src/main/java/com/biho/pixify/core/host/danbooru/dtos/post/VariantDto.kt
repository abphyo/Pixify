package com.biho.pixify.core.host.danbooru.dtos.post

import com.biho.pixify.core.model.danbooru.model.post.Media
import kotlinx.serialization.Serializable

@Serializable
@Suppress("PropertyName")
data class VariantDto(
    val file_ext: String,
    val height: Int,
    val type: String,
    val url: String,
    val width: Int
)

fun VariantDto.toMedia(): Media {
    return Media(
        url = url,
        fileType = type,
        fileExtension = file_ext,
        width = width,
        height = height
    )
}