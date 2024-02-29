package com.biho.pixify.core.model.danbooru.model.profile

import kotlinx.serialization.Serializable

@Serializable
enum class ContentFilter {
    None,
    Moderate,
    Aggressive
}
