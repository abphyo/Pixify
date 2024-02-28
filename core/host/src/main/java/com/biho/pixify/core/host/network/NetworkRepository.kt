package com.biho.pixify.core.host.network


interface NetworkRepository {
    fun getFileSize(url: String)
    fun downloadFile(url: String)
}