package com.biho.home.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.resources.theme.LocalCornerRadiusMedium

@Composable
fun PostGridItem(
    post: Post,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    Box(modifier = modifier
        .aspectRatio(post.aspectRatio)
        .clip(RoundedCornerShape(LocalCornerRadiusMedium.current))
        .clickable {
            onClick(post.id)
        }
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
    }

}