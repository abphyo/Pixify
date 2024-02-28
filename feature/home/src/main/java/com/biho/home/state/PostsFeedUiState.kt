package com.biho.home.state

import com.biho.pixify.core.model.danbooru.model.post.Post

sealed interface PostsFeedUiState {
    data class Success(val data: List<Post>): PostsFeedUiState
    data class Error(val error: String): PostsFeedUiState
    data object Loading: PostsFeedUiState
}