package com.biho.home.core

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.biho.ui.model.BuildDropDownMenu
import com.biho.resources.R
import com.biho.ui.model.PixiMenuItem

@Composable
fun HomeDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onExit: () -> Unit,
) {
    BuildDropDownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        items = listOf(
            PixiMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.refresh))
                },
                onClick = onRefresh,
                modifier = modifier
            ),
            PixiMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.exit))
                },
                onClick = onExit,
                modifier = modifier
            )
        )
    )
}