package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.repository.ImageBoardRepository
import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.pixify.core.model.util.UiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPost(private val repoImpl: ImageBoardRepository) {
    operator fun invoke(id: Int): Flow<Result<Post?>> {
        return flow {
            val post = repoImpl.getPost(id)
            emit(UiResult.invoke(post))
        }
    }
}