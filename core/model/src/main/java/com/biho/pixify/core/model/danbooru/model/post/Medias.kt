package com.biho.pixify.core.model.danbooru.model.post

data class Medias(
    val original: Media?,
    val scaled: Media?,
    val preview: Media?,
    val small: Media?,
    val extraSmall: Media?
)