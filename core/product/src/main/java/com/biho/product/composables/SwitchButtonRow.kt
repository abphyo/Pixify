package com.biho.product.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.biho.resources.theme.DIMENS_275dp
import com.biho.resources.theme.DIMENS_280dp
import com.biho.resources.theme.DIMENS_300dp

@Composable
fun SwitchButtonRow(
    leadingText: String,
    additionalText: String?,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
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
        Switch(
            checked = checked,
            enabled = true,
            onCheckedChange = onCheckedChange
        )
    }

}