package com.biho.library

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun libraryModule() = module {
    viewModelOf(::LibraryViewModel)
}