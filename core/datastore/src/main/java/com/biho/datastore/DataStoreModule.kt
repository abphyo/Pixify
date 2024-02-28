package com.biho.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun dataStoreModule() = module {
    singleOf(::provideProfileSettingsSession)
}

fun provideProfileSettingsSession(appContext: Context, scope: CoroutineScope): DataStore<ProfileEditField> {
    return DataStoreFactory.create(
        serializer = ProfileEditFieldSerializer,
        produceFile = { appContext.dataStoreFile("profile-settings-session.json") },
        corruptionHandler = null,
        scope = scope
    )
}