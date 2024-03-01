package com.biho.database

import com.biho.database.entity.User
import com.biho.pixify.core.model.danbooru.model.profile.LocalProfileSettings
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.model.profile.ProfileData
import com.biho.pixify.core.model.danbooru.model.profile.ProfileSettings
import java.util.UUID

fun User.toProfile(): Profile {
    return Profile(
        roomId = roomId,
        uuid = uuid,
        engine = engine,
        id = id,
        name = name,
        apiKey = apiKey,
        isActive = isActive,
        isGuest = isGuest,
        profileData = ProfileData(
            uploads = uploads,
            tagEdits = tagEdits,
            noteEdits = noteEdits,
            favGroups = favGroups,
            comments = comments,
            forumPosts = forumPosts
        ),
        profileSettings = ProfileSettings(
            hideDeletedPosts = hideDeletedPosts,
            enabledSafeMode = enabledSafeMode,
            postScreenImagePref = postScreenImagePref,
            receiveEmailNotifications = receiveEmailNotifications,
            blackListTags = blackListTags,
            favouriteTags = favouriteTags
        ),
        localProfileSettings = LocalProfileSettings(
            contentImagePref = contentImagePref,
            contentFiltering = contentFiltering
        )
    )
}

fun Profile.toUser(): User {
    return User(
        uuid = uuid ?: UUID.randomUUID().toString(),
        engine = engine,
        id = id ?: 0,
        name = name ?: "Guest",
        apiKey = apiKey ?: "",
        isActive = isActive,
        isGuest = isGuest,
        uploads = profileData.uploads,
        tagEdits = profileData.tagEdits,
        noteEdits = profileData.noteEdits,
        favGroups = profileData.favGroups,
        comments = profileData.comments,
        forumPosts = profileData.forumPosts,
        hideDeletedPosts = profileSettings.hideDeletedPosts,
        enabledSafeMode = profileSettings.enabledSafeMode,
        postScreenImagePref = profileSettings.postScreenImagePref,
        receiveEmailNotifications = profileSettings.receiveEmailNotifications,
        blackListTags = profileSettings.blackListTags,
        favouriteTags = profileSettings.favouriteTags,
        contentImagePref = localProfileSettings.contentImagePref,
        contentFiltering = localProfileSettings.contentFiltering
    )
}