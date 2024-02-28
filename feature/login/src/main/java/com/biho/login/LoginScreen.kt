package com.biho.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.constraintlayout.compose.ConstraintLayout
import com.biho.login.core.Authentication
import com.biho.login.state.LoginState
import com.biho.product.appbars.BackOnlyTopAppBar
import com.biho.product.composables.HeaderSlider
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_180dp
import com.biho.resources.theme.DIMENS_32dp
import com.biho.ui.model.TopAppBars

@Composable
fun LoginScreen(
    loginState: LoginState,
    username: TextFieldValue,
    apiKey: TextFieldValue,
    onNavigateBack: () -> Unit,
    navigateToProfile: () -> Unit,
    updateUsername: (TextFieldValue) -> Unit,
    updateApiKey: (TextFieldValue) -> Unit,
    login: () -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = loginState) {
        when {
            loginState == LoginState.Success -> {
                navigateToProfile()
                Toast
                    .makeText(context, "Login success!", Toast.LENGTH_SHORT)
                    .show()
            }

            loginState.error != null ->
                Toast
                    .makeText(context, loginState.error, Toast.LENGTH_LONG)
                    .show()
        }
    }

    Scaffold(
        topBar = {
            BackOnlyTopAppBar(
                type = TopAppBars.CenterAligned,
                isRouteFirstEntry = false,
                text = { Text(text = stringResource(id = R.string.login)) },
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),

        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
            ) {
                val (header, loginBox) = createRefs()
                Box(
                    modifier = Modifier
                        .constrainAs(header) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    HeaderSlider()
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(loginBox) {
                            top.linkTo(parent.top, DIMENS_180dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(topStart = DIMENS_32dp, topEnd = DIMENS_32dp)
                ) {
                    Authentication(
                        username = username,
                        apiKey = apiKey,
                        updateUsername = updateUsername,
                        updateApiKey = updateApiKey,
                        login = login
                    )
                }
            }
        }
    }

}

