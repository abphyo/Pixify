package com.biho.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class PixiTabItem(
    val text: String,
    val onClick: (() -> Unit)? = null,
    val enabled: Boolean = true,
    val modifier: Modifier = Modifier,
    val icon: @Composable (() -> Unit)? = null
)