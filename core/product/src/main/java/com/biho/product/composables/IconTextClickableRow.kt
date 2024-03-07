package com.biho.product.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_8dp
import com.biho.resources.theme.gray

@Composable
fun IconTextClickableRow(
    enabled: Boolean,
    text: @Composable () -> Unit,
    icon: @Composable (() -> Unit),
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = DIMENS_8dp)
            .clickable(
                enabled = enabled
            ) {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = if (enabled) Color.Transparent else gray.copy(alpha = 0.95f)
        ) {
            icon()
            Spacer(modifier = Modifier.width(DIMENS_8dp))
            text()
        }
    }

}