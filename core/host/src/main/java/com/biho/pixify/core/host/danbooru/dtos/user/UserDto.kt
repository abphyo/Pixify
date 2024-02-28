package com.biho.pixify.core.host.danbooru.dtos.user


import com.biho.pixify.core.model.danbooru.model.profile.toLevel
import com.biho.pixify.core.model.danbooru.model.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("appeal_count")
    val appealCount: Int?,
    @SerialName("artist_commentary_version_count")
    val artistCommentaryVersionCount: Int?,
    @SerialName("artist_version_count")
    val artistVersionCount: Int?,
    @SerialName("comment_count")
    val commentCount: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("favorite_group_count")
    val favoriteGroupCount: Int,
    @SerialName("flag_count")
    val flagCount: Int?,
    @SerialName("forum_post_count")
    val forumPostCount: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("inviter_id")
    val inviterId: Int?,
    @SerialName("is_banned")
    val isBanned: Boolean,
    @SerialName("is_deleted")
    val isDeleted: Boolean,
    @SerialName("level")
    val level: Int,
    @SerialName("level_string")
    val levelString: String?,
    @SerialName("name")
    val name: String,
    @SerialName("negative_feedback_count")
    val negativeFeedbackCount: Int?,
    @SerialName("neutral_feedback_count")
    val neutralFeedbackCount: Int?,
    @SerialName("note_update_count")
    val noteUpdateCount: Int,
    @SerialName("pool_version_count")
    val poolVersionCount: Int?,
    @SerialName("positive_feedback_count")
    val positiveFeedbackCount: Int?,
    @SerialName("post_update_count")
    val postUpdateCount: Int,
    @SerialName("post_upload_count")
    val postUploadCount: Int,
    @SerialName("wiki_page_version_count")
    val wikiPageVersionCount: Int?
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        level = level.toLevel(),
        isBanned = isBanned,
        isDeleted = isDeleted,
        createdAt = createdAt,
        uploads = postUploadCount,
        tagEdits = postUpdateCount,
        noteEdits = noteUpdateCount,
        favGroups = favoriteGroupCount,
        comments = commentCount,
        forumPosts = forumPostCount
    )
}