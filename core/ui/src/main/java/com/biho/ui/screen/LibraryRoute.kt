package com.biho.ui.screen

sealed class LibraryRoute(val route: String) {
    data object Library: LibraryRoute(route = "library_entry")
    data object Profile: LibraryRoute(route = "account_profile")
    data object CreateProfile: LibraryRoute(route = "create_profile")
    data object EditProfile: LibraryRoute(route = "edit_profile")
    data object Favourites: LibraryRoute(route = "favourites")
    data object Bookmarks: LibraryRoute(route = "bookmarks")
    data object Settings: LibraryRoute(route = "settings")
}