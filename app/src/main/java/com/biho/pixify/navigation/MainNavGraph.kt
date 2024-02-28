package com.biho.pixify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.biho.home.navigation.homeRoute
import com.biho.library.navigation.libraryRoute
import com.biho.post.navigation.postRoute
import com.biho.search.navigation.searchRoute
import com.biho.ui.screen.MainRoute

@Composable
fun MainNavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = MainRoute.Home.route) {
        homeRoute(navController = navHostController)
//        composable(
//            route = MainRoute.Post.route + "/postId={postId}",
//            arguments = listOf(
//                navArgument("postId") {
//                    type = NavType.IntType
//                    defaultValue = 6095806
//                }
//            )
//        ) { entry ->
//            PostScreen(postId = entry.arguments?.getInt("postId"))
//        }
        postRoute(navController = navHostController)
        searchRoute()
        libraryRoute(navController = navHostController)
    }
}