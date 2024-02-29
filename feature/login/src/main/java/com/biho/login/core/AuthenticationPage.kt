package com.biho.login.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.DIMENS_8dp
import com.biho.resources.theme.TEXT_SIZE_14sp
import com.biho.resources.theme.gray

@Composable
fun AuthenticationPage(
    username: TextFieldValue,
    apiKey: TextFieldValue,
    updateUsername: (TextFieldValue) -> Unit,
    updateApiKey: (TextFieldValue) -> Unit,
) {
    var textField1Empty by rememberSaveable {
        mutableStateOf(false)
    }
    var textField2Empty by rememberSaveable {
        mutableStateOf(false)
    }

    fun validateTextField1(text: String) {
        textField1Empty = text.isEmpty()
    }

    fun validateTextField2(text: String) {
        textField2Empty = text.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(DIMENS_8dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(DIMENS_16dp))
        OutlinedTextField(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .fillMaxWidth()
                .wrapContentHeight(),
            textStyle = TextStyle(
                fontSize = TEXT_SIZE_14sp,
                fontWeight = FontWeight.Light
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            value = username,
            onValueChange = {
                println("username: ${it.text}")
                updateUsername(it)
                validateTextField1(it.text)
            },
            placeholder = { Text(text = "eg. Micheal236", color = gray) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent,
            ),
            label = {
                Text(
                    text = stringResource(id = R.string.username),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = null,
            trailingIcon = null,
            supportingText = {
                if (textField1Empty)
                    Text(
                        text = "apiKey can't be empty",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
            }
        )
        Spacer(modifier = Modifier.height(DIMENS_8dp))
        OutlinedTextField(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .fillMaxWidth()
                .wrapContentHeight(),
            textStyle = TextStyle(
                fontSize = TEXT_SIZE_14sp,
                fontWeight = FontWeight.Light
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            value = apiKey,
            onValueChange = {
                println("apikey: ${it.text}")
                updateApiKey(it)
                validateTextField2(it.text)
            },
            placeholder = { Text(text = "eg. ADMCI56JCDKSAK12KS9E7", color = gray) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent,
            ),
            label = {
                Text(
                    text = stringResource(id = R.string.apiKey),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = null,
            trailingIcon = null,
            supportingText = {
                if (textField2Empty)
                    Text(
                        text = "apiKey can't be empty",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
            }
        )
    }
}