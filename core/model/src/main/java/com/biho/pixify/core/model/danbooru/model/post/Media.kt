package com.biho.pixify.core.model.danbooru.model.post

import java.security.cert.Extension

data class Media(
    val url: String,
    val width: Int,
    val height: Int,
    val fileType: String,
    val fileExtension: String
)