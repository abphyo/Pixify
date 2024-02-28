package com.biho.product.appbars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.biho.resources.R
import com.biho.ui.model.TopAppBars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackOnlyTopAppBar(
    type: TopAppBars,
    isRouteFirstEntry: Boolean,
    text: @Composable () -> Unit,
    onNavigateBack: () -> Unit
) {
    MaterialTopAppBar(
        type = type,
        title = text,
        navigationIcon = {
            if (!isRouteFirstEntry) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = stringResource(
                            id = R.string.back_click
                        )
                    )
                }
            }
        },
        actions = { },
        scrollBehavior = null
    )
}