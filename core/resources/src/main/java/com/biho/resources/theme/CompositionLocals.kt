package com.biho.resources.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.Channel

val LocalScrollToTopChannel = compositionLocalOf<Channel<String>> { error("no localScrollToTopChannel is provided") }
val LocalGridItemPaddingSmall = compositionLocalOf { 8.dp }
val LocalGridItemPaddingMedium = compositionLocalOf { 16.dp }
val LocalGridItemPaddingLarge = compositionLocalOf { 32.dp }
val LocalGridItemMinSize = compositionLocalOf { 100.dp }
val LocalCornerRadiusMedium = compositionLocalOf { 8.dp }
val LocalCornerRadiusSmall = compositionLocalOf { 4.dp }
val LocalButtonGradient = compositionLocalOf<Brush> { error("no localButtonGradient is provided") }
val LocalLexFontFamily = compositionLocalOf<FontFamily> { error("no localFontFamily is provided") }