package com.biho.pixify.core.model.danbooru.model.user

import com.biho.pixify.core.model.danbooru.model.profile.Level

data class User(
    val id: Int,
    val name: String,
    val level: Level,
    val isBanned: Boolean,
    val isDeleted: Boolean,
    val createdAt: String,
    val uploads: Int,
    val tagEdits: Int,
    val noteEdits: Int,
    val favGroups: Int,
    val comments: Int,
    val forumPosts: Int
)
