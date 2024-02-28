package com.biho.pixify.core.host.danbooru.dtos.post

import com.biho.pixify.core.model.danbooru.model.post.Medias
import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.pixify.core.model.danbooru.model.post.Rating
import com.biho.pixify.core.model.danbooru.model.post.Status
import com.biho.pixify.core.model.danbooru.model.post.Type
import com.biho.pixify.core.model.danbooru.model.post.MediaType
import com.biho.pixify.core.model.util.UnitConvertor
import com.biho.pixify.core.model.util.formatTimeAgo
import kotlinx.serialization.Serializable

@Serializable
@Suppress("PropertyName")
data class PostDto(
    val approver_id: Int?,
    val bit_flags: Int,
    val created_at: String,
    val down_score: Int,
    val fav_count: Int,
    val file_ext: String,
    val file_size: Int,
    val file_url: String,
    val has_active_children: Boolean,
    val has_children: Boolean,
    val has_large: Boolean,
    val has_visible_children: Boolean,
    val id: Int,
    val image_height: Int,
    val image_width: Int,
    val is_banned: Boolean,
    val is_deleted: Boolean,
    val is_flagged: Boolean,
    val is_pending: Boolean,
    val large_file_url: String,
    val last_comment_bumped_at: String?,
    val last_commented_at: String?,
    val last_noted_at: String?,           // TODO: unresolved type "Any"
    val md5: String?,
    val media_asset: MediaAssetDto,
    val parent_id: Int?,
    val pixiv_id: Int?,
    val preview_file_url: String,
    val rating: String,
    val score: Int,
    val source: String?,
    val tag_count: Int,
    val tag_count_artist: Int,
    val tag_count_character: Int,
    val tag_count_copyright: Int,
    val tag_count_general: Int,
    val tag_count_meta: Int,
    val tag_string: String,
    val tag_string_artist: String,
    val tag_string_character: String,
    val tag_string_copyright: String,
    val tag_string_general: String,
    val tag_string_meta: String,
    val up_score: Int,
    val updated_at: String,
    val uploader_id: Int
)

fun PostDto.toPost(): Post {

    return Post(
        id = id,
        createdAt = created_at.formatTimeAgo(),
        uploaderId = uploader_id,
        source = pixiv_id?.let { "https://pixiv.net/artworks/$it" } ?: source,
        rating = when (rating) {
            "g" -> Rating.GENERAL
            "s" -> Rating.SENSITIVE
            "q" -> Rating.QUESTIONABLE
            "e" -> Rating.EXPLICIT
            else -> Rating.EXPLICIT
        },
        status = when {
            is_banned -> Status.BANNED
            is_deleted -> Status.DELETED
            is_pending -> Status.PENDING
            is_flagged -> Status.FLAGGED
            else -> Status.ACTIVE
        },
        score = score,
        upScore = up_score,
        downScore = down_score,
        favorites = fav_count,
        medias = Medias(
            original = media_asset.variants?.firstOrNull { it.type == MediaType.Original.ratio }
                ?.toMedia(),
            scaled = media_asset.variants?.firstOrNull { it.type == MediaType.Scaled.ratio }
                ?.toMedia(),
            preview = media_asset.variants?.firstOrNull { it.type == MediaType.Preview.ratio }
                ?.toMedia(),
            small = media_asset.variants?.firstOrNull { it.type == MediaType.Small.ratio }
                ?.toMedia(),
            extraSmall = media_asset.variants?.firstOrNull { it.type == MediaType.ExtraSmall.ratio }
                ?.toMedia()
        ),
        aspectRatio = UnitConvertor.coerceAspectRatio(image_width, image_height),
        tags = tag_string.split(' '),
        type = when (file_ext) {
            in listOf("jpg", "jpeg", "png", "webp") -> Type.IMAGE
            "gif" -> Type.ANIMATED_GIF
            in listOf("mp4", "webm") -> Type.VIDEO
            "zip" -> Type.ARCHIVE
            else -> Type.IMAGE
        }
    )

}