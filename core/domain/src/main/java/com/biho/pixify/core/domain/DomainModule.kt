package com.biho.pixify.core.domain

import com.biho.pixify.core.domain.usecases.DeleteUserFromDatabase
import com.biho.pixify.core.domain.usecases.EditProfileSettings
import com.biho.pixify.core.domain.usecases.GetPosts
import com.biho.pixify.core.domain.usecases.GetTagAutoCompletes
import com.biho.pixify.core.domain.usecases.GetUsersFromDatabase
import com.biho.pixify.core.domain.usecases.LoginAsGuest
import com.biho.pixify.core.domain.usecases.LoginToHost
import com.biho.pixify.core.domain.usecases.SwitchActiveUserToDatabase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun domainModule() = module {
    factoryOf(::GetPosts)
    factoryOf(::GetPosts)
    factoryOf(::GetTagAutoCompletes)
    factoryOf(::LoginAsGuest)
    factoryOf(::LoginToHost)
    factoryOf(::GetUsersFromDatabase)
    factoryOf(::DeleteUserFromDatabase)
    factoryOf(::SwitchActiveUserToDatabase)
    factoryOf(::EditProfileSettings)
}