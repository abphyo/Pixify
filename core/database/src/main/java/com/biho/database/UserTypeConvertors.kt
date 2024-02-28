package com.biho.database

import androidx.room.TypeConverter
import com.biho.pixify.core.model.danbooru.model.post.MediaType
import com.biho.pixify.core.model.danbooru.model.profile.ContentFilter
import com.biho.pixify.core.model.danbooru.model.profile.Engine
import com.biho.pixify.core.model.danbooru.model.profile.PostScreenImageType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserTypeConvertors {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun fromEngine(engine: Engine): String {
        return engine.name
    }

    @TypeConverter
    fun toEngine(name: String): Engine {
        return Engine.valueOf(name)
    }

    @TypeConverter
    fun fromMediaType(mediaType: MediaType): String {
        return mediaType.name
    }

    @TypeConverter
    fun toMediaType(name: String): MediaType {
        return MediaType.valueOf(name)
    }

    @TypeConverter
    fun fromContentFilter(contentFilter: ContentFilter): String {
        return contentFilter.name
    }

    @TypeConverter
    fun toContentFilter(name: String): ContentFilter {
        return ContentFilter.valueOf(name)
    }

    @TypeConverter
    fun fromPostScreenImageType(postScreenImageType: PostScreenImageType): String {
        return postScreenImageType.name
    }

    @TypeConverter
    fun toPostScreenImageType(name: String): PostScreenImageType {
        return PostScreenImageType.valueOf(name)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return json.decodeFromString(value)
    }
}