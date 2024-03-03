package com.biho.pixify.core.host.danbooru

import android.content.Context
import com.biho.pixify.core.host.danbooru.dtos.login.ProfileDto
import com.biho.pixify.core.host.danbooru.dtos.login.ProfileSettingsField
import com.biho.pixify.core.host.danbooru.dtos.login.ProfileSettingsFieldData
import com.biho.pixify.core.host.danbooru.dtos.login.toProfile
import com.biho.pixify.core.host.network.ImageBoardApiCall
import com.biho.pixify.core.host.danbooru.dtos.post.PostDto
import com.biho.pixify.core.host.danbooru.dtos.post.toPost
import com.biho.pixify.core.host.danbooru.dtos.tag.TagAutoCompleteDtoList
import com.biho.pixify.core.host.danbooru.dtos.tag.toTagAutoCompleteList
import com.biho.pixify.core.host.network.AuthInterceptor
import com.biho.pixify.core.host.network.HostType
import com.biho.pixify.core.host.network.provideAuthClient
import com.biho.pixify.core.host.network.provideClient
import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import com.biho.pixify.core.model.danbooru.model.profile.isGuest
import com.biho.pixify.core.model.danbooru.model.tag.TagAutoComplete
import com.biho.pixify.core.model.danbooru.repository.UserRepository
import com.biho.pixify.core.model.util.DomainResult
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient

class ImageBoardRepoImpl(
    private val appContext: Context,
    userRepository: UserRepository
) : ImageBoardApiCall() {

    private val activeProfile = userRepository.activeUser.value

    private val hostType: HostType = HostType.SAFEBOORU

    private val authOkHttpClient = getAuthOkHttpClient(
        name = activeProfile.name.orEmpty(),
        apiKey = activeProfile.apiKey.orEmpty()
    )

    private val clientNoAuth = provideClient(appContext, hostType)

    private val clientAuth = provideAuthClient(hostType, authOkHttpClient)

    private val client = when {
        activeProfile.isGuest() -> clientNoAuth
        else -> clientAuth
    }

    override suspend fun login(name: String, apiKey: String): DomainResult<Profile> {
        return toDomain(
            { clientNoAuth.login(login = name, apiKey = apiKey) },
            { profileDto: ProfileDto -> profileDto.toProfile() }
        )
    }

    override suspend fun getProfile(): DomainResult<Profile> {
        return toDomain(
            { clientAuth.getProfile() },
            { profileDto: ProfileDto -> profileDto.toProfile() }
        )
    }

    override suspend fun updateProfileSettings(field: ProfileEditField): DomainResult<Unit> {
        return fromDomain(
            { data: ProfileSettingsField ->
                clientAuth.updateProfileSettings(
                    id = field.userId,
                    data = data
                )
            },
            {
                ProfileSettingsField(
                    data = ProfileSettingsFieldData(
                        enableSafeMode = field.enabledSafeMode,
                        showDeletedPosts = !field.hideDeletedPosts,
                        defaultImageSize = field.postScreenImageType.type,
                        blacklistedTags = field.blacklistedTags.joinToString(separator = "\n")
                    )
                )
            }
        )
    }

    private fun getAuthOkHttpClient(name: String, apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(appContext))
            .addInterceptor(AuthInterceptor(name = name, apiKey = apiKey))
            .build()
    }

    override suspend fun getPost(id: Int): DomainResult<Post> {
        return toDomain(
            { client.getPost(id) },
            { postDto: PostDto -> postDto.toPost() }
        )
    }

    override suspend fun getPosts(tags: String, page: Int): DomainResult<List<Post>> {
        return toDomain(
            { client.getPosts(tags, page) },
            { list: List<PostDto> -> list.map { it.toPost() } }
        )
    }

    override suspend fun getTagAutocompletes(query: String): DomainResult<List<TagAutoComplete>> {
        return toDomain(
            { client.getTagsAutoComplete(query) },
            { list: TagAutoCompleteDtoList -> list.toTagAutoCompleteList() }
        )
    }
}