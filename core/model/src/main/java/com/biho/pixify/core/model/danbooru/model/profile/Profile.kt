package com.biho.pixify.core.model.danbooru.model.profile

import com.biho.pixify.core.model.danbooru.model.post.MediaType
import kotlinx.collections.immutable.toPersistentList

data class Profile(
    val roomId: Int? = null,
    val uuid: String? = null,
    val engine: Engine = Engine.SAFEBOORU,
    val id: Int? = null,
    val name: String? = null,
    val apiKey: String? = null,
    val level: Level = Level.Lurker,
    val isActive: Boolean = true,
    val isGuest: Boolean = true,
    val profileData: ProfileData = ProfileData(),
    val profileSettings: ProfileSettings = ProfileSettings(),
    val localProfileSettings: LocalProfileSettings = LocalProfileSettings()
)

data class ProfileData(
    val uploads: Int = 0,
    val tagEdits: Int = 0,
    val noteEdits: Int = 0,
    val favGroups: Int = 0,
    val comments: Int = 0,
    val forumPosts: Int = 0
)

data class ProfileSettings(
    val hideDeletedPosts: Boolean = false,
    val enabledSafeMode: Boolean = true,
    val postScreenImagePref: PostScreenImageType = PostScreenImageType.Large,
    val receiveEmailNotifications: Boolean = true,
    val blackListTags: List<String> = emptyList(),
    val favouriteTags: List<String> = emptyList()
)

data class LocalProfileSettings(
    val contentImagePref: MediaType = MediaType.Preview,
    val contentFiltering: ContentFilter = ContentFilter.Aggressive,
)

enum class PostScreenImageType(val type: String) {
    Large(type = "large"),
    Original(type = "original")
}

fun Profile.isGuest(): Boolean = isGuest

fun Profile.getProfileEditField(): ProfileEditField {
    return ProfileEditField(
        userId = id ?: 0,
        roomId = roomId,
        username = name ?: "",
        apiKey = apiKey ?: "",
        enabledSafeMode = profileSettings.enabledSafeMode,
        hideDeletedPosts = profileSettings.hideDeletedPosts,
        postScreenImageType = profileSettings.postScreenImagePref,
        contentFilter = localProfileSettings.contentFiltering,
        homeScreenImageType = localProfileSettings.contentImagePref,
        blacklistedTags = profileSettings.blackListTags.toPersistentList()
    )
}

fun Profile.bindProfileEditField(field: ProfileEditField): Profile {
    return this.copy(
        profileSettings = ProfileSettings(
            enabledSafeMode = field.enabledSafeMode,
            hideDeletedPosts = field.hideDeletedPosts,
            postScreenImagePref = field.postScreenImageType,
            blackListTags = field.blacklistedTags.toList()
        ),
        localProfileSettings = LocalProfileSettings(
            contentFiltering = field.contentFilter,
            contentImagePref = field.homeScreenImageType
        )
    )
}
