package com.biho.pixify.core.host.danbooru.dtos.tag

import com.biho.pixify.core.model.danbooru.model.tag.TagAutoComplete
import kotlinx.serialization.Serializable

@Serializable
class TagAutoCompleteDtoList : ArrayList<TagAutoCompleteDto>()

fun TagAutoCompleteDtoList.toTagAutoCompleteList(): List<TagAutoComplete> {
    return this.map {
        it.toTagAutocomplete()
    }
}