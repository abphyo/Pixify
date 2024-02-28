package com.biho.pixify.core.host.danbooru.dtos.tag

import com.biho.pixify.core.model.danbooru.model.tag.TagAutoComplete
import kotlinx.serialization.Serializable

@Serializable
data class TagAutoCompleteDto(
    val category: Int,
    val label: String,
    val post_count: Int,
    val type: String,
    val value: String
)

fun TagAutoCompleteDto.toTagAutocomplete(): TagAutoComplete {
    return TagAutoComplete(
        category = category,
        label = label,
        postCount = post_count,
        type = type,
        value = value
    )
}