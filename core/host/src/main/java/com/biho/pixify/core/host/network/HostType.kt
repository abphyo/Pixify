package com.biho.pixify.core.host.network

enum class HostType {
    DANBOORU,
    DONMAIMOE,
    SAFEBOORU,
    TESTBOORU,
}

fun HostType.getBaseUrl(): String = when(this) {
    HostType.DONMAIMOE ->  "https://donmai.moe/"
    else -> this.name.lowercase().let { "https://$it.donmai.us/" }
}

fun HostType.getPostUrl(postId: Int): String = this.name.lowercase().let { "https://$it.donmai.us/$postId" }