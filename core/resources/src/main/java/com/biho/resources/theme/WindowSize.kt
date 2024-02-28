package com.biho.resources.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalWindowSizeInfo = compositionLocalOf<WindowSizeInfo> { error("no localWindowSizeInfo is provided") }

@Composable
fun rememberWindowSize(): WindowSizeInfo {
    val configuration = LocalConfiguration.current
    return remember(configuration) {
        WindowSizeInfo(
            width = configuration.screenWidthDp.dp,
            height = configuration.screenHeightDp.dp,
            widthSize = when {
                configuration.screenWidthDp < 600 -> WindowSize.Compact
                configuration.screenHeightDp < 840 -> WindowSize.Medium
                else -> WindowSize.Expanded
            },
            heightSize = when {
                configuration.screenWidthDp < 480 -> WindowSize.Compact
                configuration.screenHeightDp < 900 -> WindowSize.Medium
                else -> WindowSize.Expanded
            }
        )
    }
}

data class WindowSizeInfo(
    val width: Dp,
    val height: Dp,
    val widthSize: WindowSize,
    val heightSize: WindowSize
)

sealed class WindowSize {
    data object Compact: WindowSize()
    data object Medium: WindowSize()
    data object Expanded: WindowSize()
}