package com.biho.home

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun homeModule() = module {
    viewModelOf(::HomeViewModel)
}