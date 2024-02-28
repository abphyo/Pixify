package com.biho.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class PixiMenuItem(
    val text: @Composable () -> Unit,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val modifier: Modifier = Modifier,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon:  @Composable (() -> Unit)? = null,
)
