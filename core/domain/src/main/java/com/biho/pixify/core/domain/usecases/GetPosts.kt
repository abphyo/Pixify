package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.model.PostList
import com.biho.pixify.core.model.danbooru.repository.ImageBoardRepository
import com.biho.pixify.core.model.util.Constants
import com.biho.pixify.core.model.util.mapSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPosts(private val repoImpl: ImageBoardRepository) {
    operator fun invoke(tags: String, page: Int): Flow<Result<PostList>> {
        return flow {
            val postList = repoImpl.getPosts(tags, page).mapSuccess { resultList, _ ->
                PostList(
                    posts = resultList,
                    canLoadMore = resultList.size == Constants.POSTS_PER_PAGE
                )
            }
            emit(postList)
        }
    }
}