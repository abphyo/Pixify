package com.biho.pixify.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.biho.pixify.layouts.BottomBarLayout
import com.biho.pixify.layouts.NavigationRailLayout
import com.biho.pixify.navigation.MainNavGraph
import com.biho.resources.theme.LocalWindowSizeInfo
import com.biho.resources.theme.WindowSize

@Composable
fun ScreenSizeContent(
    navController: NavHostController,
    onItemReselected: () -> Unit,
    onNavigate: (String) -> Unit,
    isNavBarVisible: Boolean,
) {
    val navHost = remember {
        movableContentOf<PaddingValues> {
            MainNavGraph(navHostController = navController)
        }
    }
    when(LocalWindowSizeInfo.current.widthSize) {
         WindowSize.Compact -> {
            BottomBarLayout(
                navHostController = navController,
                onNavigate = onNavigate,
                onItemReselected = onItemReselected,
                isNavbarVisible = isNavBarVisible
            ) {
                navHost(it)
            }
        }
        WindowSize.Medium -> {
            NavigationRailLayout(
                navHostController = navController,
                onNavigate = onNavigate,
                onItemReselected = onItemReselected,
                isNavbarVisible = isNavBarVisible
            ) {
                navHost(PaddingValues())
            }
        }
        WindowSize.Expanded -> {
            NavigationRailLayout(
                navHostController = navController,
                onNavigate = onNavigate,
                onItemReselected = onItemReselected,
                isNavbarVisible = isNavBarVisible
            ) {
                navHost(PaddingValues())
            }
        }
    }
}