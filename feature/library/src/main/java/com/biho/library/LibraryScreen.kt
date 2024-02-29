package com.biho.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.biho.library.core.CategoryCardItem
import com.biho.library.core.CategoryGrid
import com.biho.library.core.ProfilePager
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.product.extensions.noRippleClickable
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.DIMENS_8dp
import com.biho.resources.theme.LocalButtonGradient
import com.biho.resources.theme.theming_green
import com.biho.ui.screen.LibraryRoute
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LibraryScreen(
    pagerState: PagerState,
    profiles: List<Profile>,
    navigateToProfileScreen: (Int?) -> Unit,
    createProfile: () -> Unit,
    selectProfile: (Int?) -> Unit,
    editProfile: (Int?) -> Unit,
    deleteProfile: (Profile) -> Unit,
    onCategoryItemClick: (String) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start

        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = DIMENS_16dp)
                    .padding(top = DIMENS_16dp, bottom = DIMENS_8dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.accounts),
                style = MaterialTheme.typography.titleLarge
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = DIMENS_16dp)
                    .padding(bottom = DIMENS_16dp),
                contentAlignment = Alignment.BottomStart
            ) {
                ProfilePager(
                    pagerState = pagerState,
                    profiles = profiles,
                    onItemSelect = selectProfile,
                    onItemClick = navigateToProfileScreen,
                    onItemEdit = editProfile,
                    onItemDelete = deleteProfile
                )
                Text(
                    modifier = Modifier
                        .padding(start = DIMENS_8dp)
                        .noRippleClickable { createProfile() },
                    text = stringResource(id = R.string.add_account),
                    style = MaterialTheme.typography.bodySmall.copy(color = theming_green)
                )
            }
            CategoryGrid(
                categories = listOf(
                    CategoryCardItem(
                        text = stringResource(id = R.string.favourites),
                        icon = Icons.Filled.Favorite,
                        route = LibraryRoute.Favourites.route,
                        color = LocalButtonGradient.current
                    ),
                    CategoryCardItem(
                        text = stringResource(id = R.string.bookmarks),
                        icon = Icons.Filled.ThumbUp,
                        route = LibraryRoute.Bookmarks.route,
                        color = LocalButtonGradient.current
                    ),
                    CategoryCardItem(
                        text = stringResource(id = R.string.settings),
                        icon = Icons.Filled.Settings,
                        route = LibraryRoute.Settings.route,
                        color = LocalButtonGradient.current
                    )
                ),
                contentPadding = paddingValues,
                onItemClick = onCategoryItemClick
            )
        }
    }

}