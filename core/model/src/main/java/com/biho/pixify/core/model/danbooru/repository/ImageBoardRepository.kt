package com.biho.pixify.core.model.danbooru.repository

import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import com.biho.pixify.core.model.danbooru.model.tag.TagAutoComplete
import com.biho.pixify.core.model.util.DomainResult
import kotlinx.coroutines.flow.Flow

interface ImageBoardRepository {
    suspend fun login(name: String, apiKey: String): DomainResult<Profile>
    suspend fun getProfile(): DomainResult<Profile>
    suspend fun updateProfileSettings(field: ProfileEditField): DomainResult<Unit>
    suspend fun getPost(id: Int): DomainResult<Post>
    suspend fun getPosts(tags: String, page: Int): DomainResult<List<Post>>
    suspend fun getTagAutocompletes(query: String): DomainResult<List<TagAutoComplete>>
}