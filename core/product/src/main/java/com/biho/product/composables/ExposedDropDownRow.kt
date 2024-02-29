package com.biho.product.composables

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.biho.product.appbars.BuildDropDownMenu
import com.biho.ui.model.PixiMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropDownRow(
    leadingText: String,
    additionalText: String?,
    selectedOption: String,
    dropDownItems: List<PixiMenuItem>
) {
    var dropDownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = leadingText, style = MaterialTheme.typography.bodyLarge)
            if (!additionalText.isNullOrEmpty())
                Text(text = additionalText, style = MaterialTheme.typography.bodyMedium)
        }
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {},
            modifier = Modifier
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = {
                            dropDownMenuVisible = true
                            pressOffset = DpOffset(x = it.x.toDp(), y = it.y.toDp())
                        }
                    )
                }
                .onSizeChanged {
                    itemHeight = with(density) {
                        it.height.dp
                    }

                }) {
            Text(
                text = selectedOption,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        BuildDropDownMenu(
            expanded = dropDownMenuVisible,
            onDismissRequest = { dropDownMenuVisible = false },
            items = dropDownItems
        )
    }
}