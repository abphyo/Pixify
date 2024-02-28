package com.biho.pixify.core.model.danbooru.model

import com.biho.pixify.core.model.danbooru.model.post.Post

data class PostList(
    val posts: List<Post>,
    val canLoadMore: Boolean
)
