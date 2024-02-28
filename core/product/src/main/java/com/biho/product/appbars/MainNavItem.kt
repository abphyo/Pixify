package com.biho.product.appbars

import com.biho.resources.R
import com.biho.ui.screen.MainRoute

enum class MainNavItem(
    val label: String,
    val route: String,
    val activeIcon: Int,
    val inActiveIcon: Int,
    val notificationCount: Int? = null,
    val hasNews: Boolean = false
) {
    HOME(
        label = MainRoute.Home.name,
        route = MainRoute.Home.route,
        activeIcon = R.drawable.home_filled,
        inActiveIcon = R.drawable.home
    ),
    SEARCH(
        label = MainRoute.Search.name,
        route = MainRoute.Search.route,
        activeIcon = R.drawable.search_filled,
        inActiveIcon = R.drawable.search
    ),
    LIBRARY(
        label = MainRoute.Library.name,
        route = MainRoute.Library.route,
        activeIcon = R.drawable.view_cozy_filled,
        inActiveIcon = R.drawable.view_cozy
    )
}
