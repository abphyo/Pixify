package com.biho.post.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.biho.product.composables.ScreenUnderConstruction
import com.biho.ui.screen.MainRoute
import com.biho.ui.screen.PostRoute
import com.biho.ui.screen.withStringArg
import com.biho.ui.screen.withStringArgReceiver

fun NavGraphBuilder.postRoute(navController: NavHostController) {
    navigation(
        startDestination = PostRoute.Post.route,
        route = MainRoute.Post.route
    ) {
        composable(
            route = PostRoute.Post.withStringArgReceiver("post_id"),
            arguments = listOf(
                navArgument("post_id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            val postId = entry.arguments?.getString("post_id") ?: ""
            println("postId: $postId")
            ScreenUnderConstruction()
        }
        composable(route = PostRoute.Image.route) {
            ScreenUnderConstruction()
        }
        composable(route = PostRoute.Profile.route) {
            ScreenUnderConstruction()
        }
    }
}