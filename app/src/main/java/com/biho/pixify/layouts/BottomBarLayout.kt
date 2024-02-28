package com.biho.pixify.layouts

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.biho.product.appbars.AnimatedNavBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBarLayout(
    navHostController: NavHostController,
    onNavigate: (String) -> Unit,
    onItemReselected: () -> Unit,
    modifier: Modifier = Modifier,
    isNavbarVisible: Boolean,
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedVisibility(
                visible = isNavbarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                AnimatedNavBar(
                    navHostController = navHostController,
                    onItemClick = onNavigate,
                    onItemReselected = onItemReselected
                )
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}