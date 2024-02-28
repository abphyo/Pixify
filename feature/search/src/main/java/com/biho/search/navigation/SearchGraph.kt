package com.biho.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.biho.product.composables.ScreenUnderConstruction
import com.biho.search.SearchScreen
import com.biho.ui.screen.MainRoute
import com.biho.ui.screen.SearchRoute

fun NavGraphBuilder.searchRoute() {
    navigation(
        startDestination = SearchRoute.Search.route,
        route = MainRoute.Search.route
    ) {
        composable(route = SearchRoute.Search.route) {
            ScreenUnderConstruction()
        }
    }
}