package com.biho.ui.screen

sealed class SearchRoute(val route: String) {
    data object Search: SearchRoute(route = "search_entry")
}


