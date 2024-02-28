package com.biho.login.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.biho.product.composables.CustomOutlineTextField
import com.biho.product.composables.SurveyButton
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_10dp
import com.biho.resources.theme.DIMENS_30dp
import com.biho.resources.theme.LocalLexFontFamily
import com.biho.resources.theme.TEXT_SIZE_16sp
import com.biho.resources.theme.TEXT_SIZE_22sp
import com.biho.resources.theme.theming_green

@Composable
fun Authentication(
    username: TextFieldValue,
    apiKey: TextFieldValue,
    updateUsername: (TextFieldValue) -> Unit,
    updateApiKey: (TextFieldValue) -> Unit,
    login: () -> Unit,
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

    val enabledLoginButton by remember(key1 = username, key2 = apiKey) {
        derivedStateOf {
            username.text.isNotBlank() && apiKey.text.isNotBlank()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = DIMENS_30dp)
    ) {
        val loginText = "Log in"
        val titleText = "Log in to your account"
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = DIMENS_10dp),
            fontFamily = LocalLexFontFamily.current,
            fontSize = TEXT_SIZE_22sp,
            text = buildAnnotatedString {
                append(titleText)
                addStyle(
                    style = SpanStyle(
                        color = theming_green
                    ),
                    start = 0,
                    end = loginText.length
                )
            },
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(top = DIMENS_30dp),
            text = "Username",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleSmall
        )
        CustomOutlineTextField(
            modifier = Modifier
                .padding(vertical = DIMENS_10dp),
            keyboardType = KeyboardType.Text,
            value = username,
            onValueChanged = {
                updateUsername(it)
                validateTextField1(it.text)
            },
            placeholder = "username",
            leadingIcon = R.drawable.person_24px,
            visualTransformation = VisualTransformation.None,
            supportingText = {
                if (textField1Empty)
                    Text(
                        text = "username can't be empty",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
            }
        )
        Text(
            modifier = Modifier.padding(top = DIMENS_10dp),
            text = "Api-Key",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleSmall
        )
        CustomOutlineTextField(
            modifier = Modifier.padding(vertical = DIMENS_10dp),
            keyboardType = KeyboardType.Text,
            value = apiKey,
            onValueChanged = {
                updateApiKey(it)
                validateTextField2(it.text)
            },
            placeholder = "api-key",
            leadingIcon = R.drawable.key_24px,
            visualTransformation = VisualTransformation.None,
            supportingText = {
                if (textField2Empty)
                    Text(
                        text = "apiKey can't be empty",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = DIMENS_30dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "create Api-Key",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
        }
        SurveyButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                println("clicked login button!")
                login()
            },
            text = "Login",
            isEnabled = enabledLoginButton
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = DIMENS_10dp),
            contentAlignment = Alignment.Center
        ) {
            val signText = "Sign in"
            val trailingText = "Don't have an account? "
            Text(
                fontFamily = LocalLexFontFamily.current,
                fontSize = TEXT_SIZE_16sp,
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    append(trailingText)
                    withStyle(
                        style = SpanStyle(color = theming_green)
                    ) {
                        append(signText)
                    }
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}