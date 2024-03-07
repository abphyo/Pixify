package com.biho.product.bottomsheets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    sheetState: SheetState,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    if (isVisible)
        ModalBottomSheet(
            modifier = modifier,
            containerColor = Color.Transparent,
            sheetState = sheetState,
            onDismissRequest = { onDismiss() },
            shape = RectangleShape
        ) {
            sheetContent()
        }
}