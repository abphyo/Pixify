package com.biho.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.biho.home.state.PostsFeedUiState
import com.biho.pixify.core.domain.usecases.GetPosts
import com.biho.pixify.core.model.danbooru.model.post.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(SavedStateHandleSaveableApi::class)
class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPostsUseCase: GetPosts
) : ViewModel() {

    var postsState by mutableStateOf<PostsFeedUiState>(PostsFeedUiState.Loading)
        private set

//    private val _postsState = MutableStateFlow<PostsFeedUiState>(PostsFeedUiState.Loading)
//    val postsState: StateFlow<PostsFeedUiState> get() = _postsState.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    private val posts: StateFlow<List<Post>> get() = _posts.asStateFlow()

    private var page by savedStateHandle.saveable { mutableIntStateOf(1) }

    var canLoadMore by savedStateHandle.saveable { mutableStateOf(true) }
        private set

    var isRefreshing by savedStateHandle.saveable { mutableStateOf(false) }
        private set

//    var lastPostID by savedStateHandle.saveable { mutableIntStateOf(0) }
//        private set

    private var isStartLoading by savedStateHandle.saveable { mutableStateOf(true) }
    private var getPostsJob: Job? = null

    init {
        getPosts(false)
    }

    fun getPosts(isRefresh: Boolean = false) {
        getPostsJob?.cancel()
        when {
            isStartLoading || isRefresh -> page = 1
            else -> page += 1
        }
        if (isStartLoading) postsState = PostsFeedUiState.Loading
        if (isRefresh) {
            _posts.update { emptyList() }
            isRefreshing = true
        }
        getPostsJob = viewModelScope.launch {
            getPostsUseCase.invoke(page = page, tags = "").collectLatest { result ->
                result.onSuccess { newList ->
                    canLoadMore = newList.canLoadMore
                    _posts.update { previousList ->
                        previousList.plus(newList.posts).distinctBy { it.id }
                    }
                    isStartLoading = false
                    isRefreshing = false
                    posts.collectLatest { updatedList ->
                        postsState = PostsFeedUiState.Success(updatedList)
//                        lastPostID = updatedList.last().id
                    }
                }
                result.onFailure { throwable ->
                    postsState = PostsFeedUiState.Error(throwable.message.toString())
                }
            }
        }
    }

    fun refreshGetPosts() = getPosts(isRefresh = true)
}