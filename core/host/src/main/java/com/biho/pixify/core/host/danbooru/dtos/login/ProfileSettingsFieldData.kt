package com.biho.pixify.core.host.danbooru.dtos.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileSettingsFieldData(
    @SerialName("enable_safe_mode")
    val enableSafeMode: Boolean? = null,

    @SerialName("show_deleted_posts")
    val showDeletedPosts: Boolean? = null,

    @SerialName("default_image_size")
    val defaultImageSize: String? = null,

    @SerialName("blacklisted_tags")
    val blacklistedTags: String? = null,
)



