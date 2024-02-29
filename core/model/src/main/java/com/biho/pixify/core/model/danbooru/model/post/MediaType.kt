package com.biho.pixify.core.model.danbooru.model.post

import kotlinx.serialization.Serializable

@Serializable
enum class MediaType(val ratio: String) {
    Original(ratio = "original"),
    Scaled(ratio = "scaled"),
    Preview(ratio = "720x720"),
    Small(ratio = "360x360"),
    ExtraSmall(ratio = "180x180"),
}