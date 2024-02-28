package com.biho.product.appbars

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import com.biho.ui.model.PixiMenuItem

@Composable
fun MaterialDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<PixiMenuItem>
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
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