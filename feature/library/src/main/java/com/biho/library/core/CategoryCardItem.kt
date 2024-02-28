package com.biho.library.core

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryCardItem(
    val text: String,
    val icon: ImageVector,
    val route: String,
    val enabled: Boolean = true,
    val color: Brush,
    val modifier: Modifier = Modifier
)