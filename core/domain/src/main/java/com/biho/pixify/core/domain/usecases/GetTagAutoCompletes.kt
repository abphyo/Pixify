package com.biho.pixify.core.domain.usecases

import com.biho.pixify.core.model.danbooru.repository.ImageBoardRepository
import com.biho.pixify.core.model.danbooru.model.tag.TagAutoComplete
import com.biho.pixify.core.model.util.UiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTagAutoCompletes(private val repoImpl: ImageBoardRepository) {
    operator fun invoke(query: String): Flow<Result<List<TagAutoComplete>>> {
        return flow {
            val tags = repoImpl.getTagAutocompletes(query)
            emit(UiResult.invoke(tags))
        }
    }
}