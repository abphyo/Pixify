package com.biho.home.core

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.product.bottomsheets.CustomBottomSheet
import com.biho.product.composables.IconTextClickableRow
import com.biho.resources.theme.LocalCornerRadiusMedium
import com.biho.resources.theme.LocalWindowSizeInfo
import com.biho.resources.theme.WindowSize
import com.biho.ui.model.BuildDropDownMenu
import com.biho.ui.model.PixiMenuItem

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PostGridItem(
    post: Post,
    onClick: (Int) -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    bottomSheetItems: List<PixiMenuItem>,
    bottomSheetState: SheetState,
    isDropDownExpanded: Boolean = false,
    dropDownDismiss: () -> Unit
) {

    val context = LocalContext.current
    Box(modifier = modifier
        .aspectRatio(post.aspectRatio)
        .clip(RoundedCornerShape(LocalCornerRadiusMedium.current))
        .combinedClickable(
            onClick = { onClick(post.id) },
            onLongClick = { onLongClick() }
        )
//        .combinedClickable(
//            onClick = onClick,
//            onLongClick = onLongClick,
//            interactionSource = remember { MutableInteractionSource() },
//            indication = rememberRipple(color = Color.Black)
//        )
    ) {
        PostPlaceHolder()
        if (!post.medias.preview?.url.isNullOrEmpty()) {
            AsyncImage(
                model = remember {

                    ImageRequest.Builder(context)
                        .data(post.medias.preview?.url)
                        .crossfade(durationMillis = 170)
                        .build()
                },
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        if (LocalWindowSizeInfo.current.widthSize == WindowSize.Compact)
            BuildDropDownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = { dropDownDismiss() },
                items = bottomSheetItems
            )
        if (LocalWindowSizeInfo.current.widthSize == WindowSize.Compact)
            CustomBottomSheet(
                sheetState = bottomSheetState,
                isVisible = isDropDownExpanded,
                onDismiss = dropDownDismiss,
                sheetContent = { HomeBottomSheetContent(items = bottomSheetItems) }
            )
    }

}

@Composable
fun HomeBottomSheetContent(
    items: List<PixiMenuItem>
) {
    items.forEach { item ->
        IconTextClickableRow(
            enabled = item.enabled,
            text = item.text,
            icon = item.leadingIcon!!,
            onClick = item.onClick
        )
    }
}