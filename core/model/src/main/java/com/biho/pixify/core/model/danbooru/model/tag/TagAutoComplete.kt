package com.biho.pixify.core.model.danbooru.model.tag

data class TagAutoComplete(
    val category: Int,
    val label: String,
    val postCount: Int,
    val type: String,
    val value: String
)
