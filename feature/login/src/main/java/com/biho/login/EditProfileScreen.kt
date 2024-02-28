package com.biho.login

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Tab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.biho.login.core.AuthenticationPage
import com.biho.login.core.ProfileSettingsPage
import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import com.biho.product.appbars.BackOnlyTopAppBar
import com.biho.product.composables.CustomHorizontalPager
import com.biho.product.composables.HeaderSlider
import com.biho.product.extensions.customTabIndicatorOffset
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.DIMENS_345dp
import com.biho.resources.theme.DIMENS_36dp
import com.biho.ui.model.PixiTabItem
import com.biho.ui.model.TopAppBars
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EditProfileScreen(
    profileEditField: ProfileEditField,
    onUsernameChanged: (TextFieldValue) -> Unit,
    onApiKeyChanged: (TextFieldValue) -> Unit,
    confirm: () -> Unit,
    onNavigateBack: () -> Unit
) {

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val interactionSource = MutableInteractionSource()
    val tabItems: List<PixiTabItem> = listOf(
        PixiTabItem(
            text = "Authentication"
        ),
        PixiTabItem(
            text = "Settings"
        )
    )
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabItems.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    val pagerState = rememberPagerState(initialPage = 0)
    LaunchedEffect(key1 = selectedIndex) {
        pagerState.animateScrollToPage(selectedIndex)
    }
    LaunchedEffect(key1 = pagerState, key2 = pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress)
            selectedIndex = pagerState.currentPage

    }
    val isButtonEnabled by remember {
        mutableStateOf(true)
    }
    Scaffold(
        topBar = {
            BackOnlyTopAppBar(
                type = TopAppBars.CenterAligned,
                isRouteFirstEntry = false,
                text = { Text(text = stringResource(id = R.string.edit_profile)) },
                onNavigateBack = onNavigateBack
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier.scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Horizontal
            )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val (header, box, button) = createRefs()
                Box(
                    modifier = Modifier
                        .constrainAs(header) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    HeaderSlider()
                }
                Box(
                    modifier = Modifier.constrainAs(box) {
                        top.linkTo(header.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        TabRow(
                            selectedTabIndex = selectedIndex,
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    modifier = Modifier.customTabIndicatorOffset(
                                        currentTabPosition = tabPositions[selectedIndex],
                                        tabWidth = tabWidths[selectedIndex]
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            tabItems.forEachIndexed { index, name ->
                                Tab(
                                    modifier = Modifier.height(DIMENS_36dp),
                                    selected = index == selectedIndex,
                                    onClick = { selectedIndex = index }
                                ) {
                                    Text(
                                        text = name.text,
                                        style = MaterialTheme.typography.bodyMedium,
                                        onTextLayout = { textLayoutResult ->
                                            tabWidths[index] =
                                                with(density) { textLayoutResult.size.width.toDp() }
                                        }
                                    )
                                }
                            }
                        }
                        Box {
                            CustomHorizontalPager(
                                pagerState = pagerState,
                                indicatorEnabled = false,
                                count = tabItems.size,
                                interactionSource = interactionSource,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(DIMENS_345dp)
                            ) { page, _ ->
                                when (page) {
                                    0 -> AuthenticationPage(
                                        username = TextFieldValue(profileEditField.username),
                                        apiKey = TextFieldValue(profileEditField.apiKey),
                                        updateUsername = onUsernameChanged,
                                        updateApiKey = onApiKeyChanged
                                    )
                                    1 -> ProfileSettingsPage()
                                }
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier.constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    Button(
                        enabled = isButtonEnabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(DIMENS_16dp),
                        onClick = confirm,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}