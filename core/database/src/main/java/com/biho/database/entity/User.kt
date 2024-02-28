package com.biho.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.biho.pixify.core.model.danbooru.model.post.MediaType
import com.biho.pixify.core.model.danbooru.model.profile.ContentFilter
import com.biho.pixify.core.model.danbooru.model.profile.Engine
import com.biho.pixify.core.model.danbooru.model.profile.PostScreenImageType

@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "room_id")
    val roomId: Int = 0,

    @ColumnInfo(name = "engine", defaultValue = "SAFEBOORU")
    val engine: Engine = Engine.SAFEBOORU,

    @ColumnInfo(name = "is_active", defaultValue = "true")
    val isActive: Boolean = true,

    @ColumnInfo(name = "is_guest", defaultValue = "true")
    val isGuest: Boolean = true,

    @ColumnInfo(name = "id", defaultValue = "0")
    val id: Int = 0,

    @ColumnInfo(name = "name", defaultValue = "okuser")
    val name: String = "okuser",

    @ColumnInfo(name = "api_key", defaultValue = "")
    val apiKey: String = "",

    @ColumnInfo(name = "uploads", defaultValue = "0")
    val uploads: Int = 0,

    @ColumnInfo(name = "tag_edits", defaultValue = "0")
    val tagEdits: Int = 0,

    @ColumnInfo(name = "note_edits", defaultValue = "0")
    val noteEdits: Int = 0,

    @ColumnInfo(name = "fav_groups", defaultValue = "0")
    val favGroups: Int = 0,

    @ColumnInfo(name = "comments", defaultValue = "0")
    val comments: Int = 0,

    @ColumnInfo(name = "forum_posts", defaultValue = "0")
    val forumPosts: Int = 0,

    @ColumnInfo(name = "hide_deleted_posts", defaultValue = "true")
    val hideDeletedPosts: Boolean = true,

    @ColumnInfo(name = "enabled_safe_mode", defaultValue = "true")
    val enabledSafeMode: Boolean = true,

    @ColumnInfo(name = "post_screen_image_pref", defaultValue = "Large")
    val postScreenImagePref: PostScreenImageType = PostScreenImageType.Large,

    @ColumnInfo(name = "receive_email_notifications", defaultValue = "false")
    val receiveEmailNotifications: Boolean = false,

    @ColumnInfo(name = "blacklist_tags")
    val blackListTags: List<String> = emptyList(),

    @ColumnInfo(name = "favourite_tags")
    val favouriteTags: List<String> = emptyList(),

    @ColumnInfo(name = "content_image_pref", defaultValue = "Scaled")
    val contentImagePref: MediaType = MediaType.Scaled,

    @ColumnInfo(name = "content_filtering", defaultValue = "Aggressive")
    val contentFiltering: ContentFilter = ContentFilter.Aggressive

)


