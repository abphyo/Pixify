package com.biho.product.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.DIMENS_32dp
import com.biho.resources.theme.DIMENS_4dp
import com.biho.resources.theme.TEXT_SIZE_16sp

@Composable
fun SurveyButton(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String,
    isEnabled: Boolean
) {
    Button(
        enabled = isEnabled,
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(DIMENS_32dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = DIMENS_4dp),
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = TEXT_SIZE_16sp
        )
    }
}

@Preview
@Composable
fun PreviewSurveyButton() {
    SurveyButton(onClick = { }, text = "Login", modifier = Modifier, isEnabled = false)
}