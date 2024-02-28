package com.biho.home.core

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.biho.product.appbars.MaterialTopAppBar
import com.biho.resources.R
import com.biho.ui.model.TopAppBars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    isRouteFirstEntry: Boolean,
    onNavigateBack: () -> Unit,
    onRefresh: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    var dropDownExpandedState by rememberSaveable {
        mutableStateOf(false)
    }
    MaterialTopAppBar(
        type = TopAppBars.CenterAligned,
        title = {
            Text(text = stringResource(id = R.string.home))
        },
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
        actions = {
            IconButton(onClick = { dropDownExpandedState = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.more_vert),
                    contentDescription = stringResource(
                        id = R.string.more_click
                    )
                )
            }
            HomeDropDownMenu(
                expanded = dropDownExpandedState,
                onDismissRequest = { dropDownExpandedState = false },
                onRefresh = {
                    dropDownExpandedState = false
                    onRefresh()
                },
                onExit = {
                    dropDownExpandedState = false
                    onExit()
                }
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}