package com.biho.ui.screen

enum class MainRoute(val route: String) {
    Home(route = "Home"),
    PostFeed(route = PostRoute.PostFeed.route),
    Post(route = "Post"),
    Search(route = "Search"),
    SearchEntry(route = SearchRoute.Search.route),
    Library(route = "Library"),
    LibraryEntry(route = LibraryRoute.Library.route)
}

val mainNavRoutes: List<String> = MainRoute.entries.map { it.route }
