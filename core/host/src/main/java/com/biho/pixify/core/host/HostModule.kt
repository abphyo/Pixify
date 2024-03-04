package com.biho.pixify.core.host

import com.biho.pixify.core.host.danbooru.ImageBoardRepoImpl
import com.biho.pixify.core.host.download.DownloadRepository
import com.biho.pixify.core.host.download.DownloadRepositoryImpl
import com.biho.pixify.core.host.network.NetworkRepository
import com.biho.pixify.core.host.network.NetworkRepositoryImpl
import com.biho.pixify.core.model.danbooru.repository.ImageBoardRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun hostModule() = module {
    singleOf(::ImageBoardRepoImpl) { bind<ImageBoardRepository>() }
    singleOf(::NetworkRepositoryImpl) { bind<NetworkRepository>() }
    singleOf(::DownloadRepositoryImpl) { bind<DownloadRepository>() }
}
