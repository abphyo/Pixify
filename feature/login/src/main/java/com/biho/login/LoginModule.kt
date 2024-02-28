package com.biho.login

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun loginModule() = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::EditProfileViewModel)
}