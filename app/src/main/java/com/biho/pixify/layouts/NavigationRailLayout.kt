package com.biho.pixify.layouts

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.biho.product.appbars.NavRail

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationRailLayout(
    navHostController: NavHostController,
    onNavigate: (String) -> Unit,
    onItemReselected: () -> Unit,
    modifier: Modifier = Modifier,
    isNavbarVisible: Boolean,
    content: @Composable () -> Unit
) {
    Row(modifier = modifier) {
        AnimatedVisibility(
            visible = isNavbarVisible,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            NavRail(navHostController = navHostController, onItemClick = onNavigate, onItemReSelected = onItemReselected)
        }
        content()
    }
}