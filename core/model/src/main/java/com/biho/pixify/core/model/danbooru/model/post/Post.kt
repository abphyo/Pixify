package com.biho.pixify.core.model.danbooru.model.post

data class Post(
    val id: Int,

    val createdAt: String,

    val uploaderId: Int,
    val source: String?,
    val rating: Rating,
    val status: Status,

    val score: Int,
    val upScore: Int,
    val downScore: Int,
    val favorites: Int,

    val type: Type,
    val medias: Medias,
    val aspectRatio: Float,

    val tags: List<String>,
)
