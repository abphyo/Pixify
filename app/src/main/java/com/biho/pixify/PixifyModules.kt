package com.biho.pixify

import com.biho.database.databaseModule
import com.biho.datastore.dataStoreModule
import com.biho.home.homeModule
import com.biho.library.libraryModule
import com.biho.login.loginModule
import com.biho.pixify.core.domain.domainModule
import com.biho.pixify.core.host.hostModule
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

object PixifyModules {
    operator fun invoke() = module {
        includes(
            listOf(
                databaseModule(),
                dataStoreModule(),
                hostModule(),
                domainModule(),
                homeModule(),
                libraryModule(),
                loginModule()
            )
        )
        single { SupervisorJob() }
        factory { CoroutineScope(context = Dispatchers.IO + get<CompletableJob>()) }
    }
}