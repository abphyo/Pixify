package com.biho.ui.screen

sealed class PostRoute(val route: String) {
    data object PostFeed: PostRoute(route = "post_feed")
    data object Post: PostRoute(route = "post")
    data object Image: PostRoute(route = "image")
    data object Profile: PostRoute(route = "profile")
}

fun PostRoute.withStringArg(arg: String): String {
    return buildString {
        append(route)
        append("/")
        append(arg)
    }
}

fun PostRoute.withStringArgReceiver(argName: String): String {
    return buildString {
        append(route)
        append("/")
        append("{$argName}")
    }
}
