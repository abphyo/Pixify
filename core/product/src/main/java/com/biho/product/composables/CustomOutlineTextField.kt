package com.biho.product.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_10dp
import com.biho.resources.theme.DIMENS_24dp
import com.biho.resources.theme.TEXT_SIZE_16sp
import com.biho.resources.theme.gray

@Composable
fun CustomOutlineTextField(
    modifier: Modifier,
    keyboardType: KeyboardType,
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    placeholder: String,
    leadingIcon: Int,
    visualTransformation: VisualTransformation,
    supportingText: @Composable () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    OutlinedTextField(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        textStyle = TextStyle(
            fontSize = TEXT_SIZE_16sp,
            fontWeight = FontWeight.Light
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        value = value,
        onValueChange = onValueChanged,
        placeholder = { Text(text = placeholder, color = gray) },
        singleLine = true,
        leadingIcon = {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = DIMENS_10dp)
                        .size(DIMENS_24dp),
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "text_field"
                )
                Canvas(modifier = Modifier.height(DIMENS_24dp)) {
                    drawLine(
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        color = Color.Black,
                        strokeWidth = 2.0f
                    )
                }
            }
        },
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor
        ),
        supportingText = supportingText
    )
}

@Preview
@Composable
fun PreviewTextField() {
    CustomOutlineTextField(
        modifier = Modifier,
        value = TextFieldValue("username"),
        onValueChanged = { },
        placeholder = "username",
        leadingIcon = R.drawable.home_filled,
        visualTransformation = VisualTransformation.None,
        keyboardType = KeyboardType.Text,
        supportingText = {
            Text(text = "error", color = MaterialTheme.colorScheme.error)
        }
    )
}