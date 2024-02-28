package com.biho.pixify.core.host.danbooru.dtos.login

import com.biho.pixify.core.model.danbooru.model.profile.LocalProfileSettings
import com.biho.pixify.core.model.danbooru.model.profile.PostScreenImageType
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.pixify.core.model.danbooru.model.profile.ProfileData
import com.biho.pixify.core.model.danbooru.model.profile.ProfileSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("appeal_count")
    val appealCount: Int?,
    @SerialName("artist_commentary_version_count")
    val artistCommentaryVersionCount: Int?,
    @SerialName("artist_version_count")
    val artistVersionCount: Int?,
    @SerialName("blacklisted_tags")
    val blacklistedTags: String?,
    @SerialName("comment_count")
    val commentCount: Int?,
    @SerialName("comment_threshold")
    val commentThreshold: Int?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("custom_style")
    val customStyle: String?,
    @SerialName("default_image_size")
    val defaultImageSize: String?,
    @SerialName("disable_categorized_saved_searches")
    val disableCategorizedSavedSearches: Boolean?,
    @SerialName("disable_mobile_gestures")
    val disableMobileGestures: Boolean?,
    @SerialName("disable_post_tooltips")
    val disablePostTooltips: Boolean?,
    @SerialName("disable_tagged_filenames")
    val disableTaggedFilenames: Boolean?,
    @SerialName("enable_desktop_mode")
    val enableDesktopMode: Boolean?,
    @SerialName("enable_private_favorites")
    val enablePrivateFavorites: Boolean?,
    @SerialName("enable_safe_mode")
    val enableSafeMode: Boolean?,
    @SerialName("favorite_count")
    val favoriteCount: Int?,
    @SerialName("favorite_group_count")
    val favoriteGroupCount: Int?,
    @SerialName("favorite_group_limit")
    val favoriteGroupLimit: Int?,
    @SerialName("favorite_tags")
    val favoriteTags: String?,
    @SerialName("flag_count")
    val flagCount: Int?,
    @SerialName("forum_post_count")
    val forumPostCount: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("inviter_id")
    val inviterId: Int?,
    @SerialName("is_banned")
    val isBanned: Boolean?,
    @SerialName("is_deleted")
    val isDeleted: Boolean?,
    @SerialName("is_verified")
    val isVerified: Boolean?,
    @SerialName("last_forum_read_at")
    val lastForumReadAt: String?,
    @SerialName("last_logged_in_at")
    val lastLoggedInAt: String?,
    @SerialName("level")
    val level: Int?,
    @SerialName("level_string")
    val levelString: String?,
    @SerialName("max_saved_searches")
    val maxSavedSearches: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("negative_feedback_count")
    val negativeFeedbackCount: Int?,
    @SerialName("neutral_feedback_count")
    val neutralFeedbackCount: Int?,
    @SerialName("new_post_navigation_layout")
    val newPostNavigationLayout: Boolean?,
    @SerialName("note_update_count")
    val noteUpdateCount: Int?,
    @SerialName("per_page")
    val perPage: Int?,
    @SerialName("pool_version_count")
    val poolVersionCount: Int?,
    @SerialName("positive_feedback_count")
    val positiveFeedbackCount: Int?,
    @SerialName("post_update_count")
    val postUpdateCount: Int?,
    @SerialName("post_upload_count")
    val postUploadCount: Int?,
    @SerialName("receive_email_notifications")
    val receiveEmailNotifications: Boolean?,
    @SerialName("requires_verification")
    val requiresVerification: Boolean?,
    @SerialName("show_deleted_children")
    val showDeletedChildren: Boolean?,
    @SerialName("show_deleted_posts")
    val showDeletedPosts: Boolean?,
    @SerialName("statement_timeout")
    val statementTimeout: Int?,
    @SerialName("tag_query_limit")
    val tagQueryLimit: Int?,
    @SerialName("theme")
    val theme: String?,
    @SerialName("time_zone")
    val timeZone: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("wiki_page_version_count")
    val wikiPageVersionCount: Int?
)

fun ProfileDto.toProfile(): Profile {
    return Profile(
        id = id,
        name = name,
        apiKey = null,
        profileData = ProfileData(
            uploads = postUploadCount ?: 0,
            tagEdits = postUpdateCount ?: 0,
            noteEdits = noteUpdateCount ?: 0,
            favGroups = favoriteGroupCount ?: 0,
            comments = commentCount ?: 0,
            forumPosts = forumPostCount ?: 0
        ),
        profileSettings = ProfileSettings(
            hideDeletedPosts = !(showDeletedPosts ?: true),
            enabledSafeMode = enableSafeMode ?: true,
            postScreenImagePref = when (defaultImageSize) {
                PostScreenImageType.Large.type -> PostScreenImageType.Large
                PostScreenImageType.Original.type -> PostScreenImageType.Original
                else -> PostScreenImageType.Large
            },
            receiveEmailNotifications = receiveEmailNotifications ?: false,
            blackListTags = blacklistedTags?.split(' ') ?: emptyList(),
            favouriteTags = favoriteTags?.split(' ') ?: emptyList()
        ),
        localProfileSettings = LocalProfileSettings()
    )
}