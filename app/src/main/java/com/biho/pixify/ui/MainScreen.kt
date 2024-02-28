package com.biho.pixify.ui
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.biho.pixify.content.ScreenSizeContent
import com.biho.resources.theme.DIMENS_5dp
import com.biho.resources.theme.LocalScrollToTopChannel
import com.biho.resources.theme.PixifyTheme
import com.biho.ui.screen.PostRoute
import com.biho.ui.screen.mainNavRoutes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentMainRoute = navBackStackEntry?.destination?.parent?.route
    val currentRoute = navBackStackEntry?.destination?.route
    val isNavBarVisible = mainNavRoutes.contains(currentRoute)
    val scrollToTopChannel = Channel<String>()
    PixifyTheme {
        // A surface container using the 'background' color from the theme
        CompositionLocalProvider(
            values = arrayOf(
                LocalScrollToTopChannel provides scrollToTopChannel
            )
        ) {
            SideEffect {
                Log.d("navbar_situation", "MainScreen: $isNavBarVisible")
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
                tonalElevation = DIMENS_5dp
            ) {
                ScreenSizeContent(
                    navController = navController,
                    onNavigate = {
                        navController.navigate(it) {
                            popUpTo(PostRoute.PostFeed.route) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    isNavBarVisible = isNavBarVisible,
                    onItemReselected = {
                        scope.launch {
                            scrollToTopChannel.send(UUID.randomUUID().toString())
                        }
                    }
                )
            }
        }
    }
}