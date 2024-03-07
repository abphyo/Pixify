package com.biho.product.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified T: ViewModel>NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val parentRoute = destination.parent?.route ?: return koinViewModel()
    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(parentRoute)
    }
    return koinViewModel<T>(viewModelStoreOwner = parentEntry)
}