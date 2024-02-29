package com.biho.product.appbars

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpOffset
import com.biho.ui.model.PixiMenuItem

@Composable
fun BuildDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<PixiMenuItem>,
    pressOffset: DpOffset
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = pressOffset
    ) {
        items.forEachIndexed { _, item ->
            item.run {
                DropdownMenuItem(
                    text = text,
                    onClick = onClick,
                    enabled = enabled,
                    modifier = modifier,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon
                )
            }
        }
    }
}