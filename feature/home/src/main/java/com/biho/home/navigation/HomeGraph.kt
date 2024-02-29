package com.biho.home.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.biho.home.HomeScreen
import com.biho.home.HomeViewModel
import com.biho.product.movable.sharedViewModel
import com.biho.ui.screen.MainRoute
import com.biho.ui.screen.PostRoute
import com.biho.ui.screen.withStringArg

fun NavGraphBuilder.homeRoute(navController: NavHostController) {
    navigation(
        startDestination = PostRoute.PostFeed.route,
        route = MainRoute.Home.route
    ) {
        composable(route = PostRoute.PostFeed.route) { entry ->
            val navigateToPostScreen = { postId: Int ->
                navController.navigate(PostRoute.Post.withStringArg(postId.toString()))
            }
            val isRouteFirstEntry = remember {
                navController.currentBackStackEntry?.destination?.route == entry.destination.route
            }
            val homeViewModel = entry.sharedViewModel<HomeViewModel>(navController = navController)
            HomeScreen(
                onPostItemClick = navigateToPostScreen,
                isRouteFirstEntry = isRouteFirstEntry,
                onNavigateBack = {
                    if (
                        navController
                            .currentBackStackEntry?.lifecycle?.currentState ==
                                Lifecycle.State.RESUMED
                        )
                        navController.navigateUp()
                },
                getPosts = homeViewModel::getPosts,
                refreshGetPosts = homeViewModel::refreshGetPosts,
                canLoadMore = homeViewModel.canLoadMore,
                postsState = homeViewModel.postsState,
                isRefreshing = homeViewModel.isRefreshing
            )

        }
    }
}