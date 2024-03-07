package com.biho.product.composables

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.biho.ui.model.BuildDropDownMenu
import com.biho.resources.theme.DIMENS_275dp
import com.biho.ui.model.PixiMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipDropDownRow(
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
//    val density = LocalDensity.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(DIMENS_275dp)
        ) {
            Text(text = leadingText, style = MaterialTheme.typography.bodyMedium)
            if (!additionalText.isNullOrEmpty())
                Text(text = additionalText, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Box {
            FilterChip(
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                selected = false,
                onClick = {
                    dropDownMenuVisible = true
                },
                label = {
                    Text(
                        text = selectedOption,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier
                    .wrapContentSize()
                    .onSizeChanged {
                        itemHeight = it.height.dp
                    }
                    .pointerInput(true) {
                        detectTapGestures(
                            onTap = {
                                pressOffset = DpOffset(x = it.x.toDp(), y = it.y.toDp())
                            }
                        )
                    }
            )
            BuildDropDownMenu(
                expanded = dropDownMenuVisible,
                onDismissRequest = { dropDownMenuVisible = false },
                items = dropDownItems,
                pressOffset = pressOffset.copy(
                    y = pressOffset.y - itemHeight
                )
            )
        }
    }
}