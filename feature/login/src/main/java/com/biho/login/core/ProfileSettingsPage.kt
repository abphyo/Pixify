package com.biho.login.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.biho.pixify.core.model.danbooru.model.post.MediaType
import com.biho.pixify.core.model.danbooru.model.profile.ContentFilter
import com.biho.pixify.core.model.danbooru.model.profile.PostScreenImageType
import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import com.biho.product.composables.ExposedDropDownRow
import com.biho.product.composables.RadioButtonRow
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.DIMENS_8dp
import com.biho.ui.model.PixiMenuItem

@Composable
fun ProfileSettingsPage(
    profileEditField: ProfileEditField,
    setContentFilter: (ContentFilter) -> Unit,
    setPostScreenImageType: (PostScreenImageType) -> Unit,
    setHomeScreenImageType: (MediaType) -> Unit,
    toggleSafeMode: (Boolean) -> Unit,
    toggleHideDeletedPost: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(DIMENS_8dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(DIMENS_16dp))
        ExposedDropDownRow(
            leadingText = stringResource(id = R.string.content_filtering),
            additionalText = null,
            selectedOption = profileEditField.contentFilter.name,
            dropDownItems = ContentFilter.entries.map { type ->
                PixiMenuItem(
                    text = { Text(text = type.name) },
                    enabled = type == ContentFilter.Aggressive,
                    onClick = {
                        setContentFilter(type)
                    }
                )
            }
        )
        Spacer(modifier = Modifier.height(DIMENS_8dp))
        ExposedDropDownRow(
            leadingText = stringResource(id = R.string.detail_image_resolution),
            additionalText = stringResource(id = R.string.image_resolution_helper_text),
            selectedOption = profileEditField.postScreenImageType.name,
            dropDownItems = PostScreenImageType.entries.map { type ->
                PixiMenuItem(
                    text = { Text(text = type.name) },
                    enabled = true,
                    onClick = {
                        setPostScreenImageType(type)
                    }
                )
            }
        )
        Spacer(modifier = Modifier.height(DIMENS_8dp))
        ExposedDropDownRow(
            leadingText = stringResource(id = R.string.home_image_resolution),
            additionalText = stringResource(id = R.string.image_resolution_helper_text),
            selectedOption = profileEditField.homeScreenImageType.name,
            dropDownItems = MediaType.entries.map { type ->
                PixiMenuItem(
                    text = { Text(text = type.name) },
                    enabled = true,
                    onClick = {
                        setHomeScreenImageType(type)
                    }
                )
            }
        )
        Spacer(modifier = Modifier.height(DIMENS_16dp))
        RadioButtonRow(
            leadingText = stringResource(
                id = R.string.enable_safe_mode
            ),
            additionalText = null,
            selected = profileEditField.enabledSafeMode,
            onClick = toggleSafeMode,
        )
        Spacer(modifier = Modifier.height(DIMENS_8dp))
        RadioButtonRow(
            leadingText = stringResource(
                id = R.string.hide_deleted_posts
            ),
            additionalText = stringResource(
                id = R.string.hide_deleted_posts_helper_text
            ),
            selected = profileEditField.hideDeletedPosts,
            onClick = toggleHideDeletedPost
        )
        Spacer(modifier = Modifier.height(DIMENS_16dp))
    }
}


