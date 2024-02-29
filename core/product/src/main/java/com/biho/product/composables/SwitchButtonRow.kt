package com.biho.product.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SwitchButtonRow(
    leadingText: String,
    additionalText: String?,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

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
        Switch(
            checked = checked,
            enabled = true,
            onCheckedChange = onCheckedChange
        )
    }

}