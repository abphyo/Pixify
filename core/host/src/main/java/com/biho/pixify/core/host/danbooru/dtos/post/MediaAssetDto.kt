package com.biho.pixify.core.host.danbooru.dtos.post

import kotlinx.serialization.Serializable

@Serializable
@Suppress("PropertyName")
data class MediaAssetDto(
    val created_at: String,
    val duration: Double?,
    val file_ext: String,
    val file_key: String?,
    val file_size: Int,
    val id: Int,
    val image_height: Int,
    val image_width: Int,
    val is_public: Boolean,
    val md5: String?,
    val pixel_hash: String,
    val status: String,
    val updated_at: String,
    val variants: List<VariantDto>?
)