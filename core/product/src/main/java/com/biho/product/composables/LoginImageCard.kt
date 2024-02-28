package com.biho.product.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginImageCard(
    page: Int,
    pagerScope: PagerScope,
    imageList: List<Int>,
    contentScale: ContentScale = ContentScale.Crop
) {
    Card(
        modifier = Modifier
            .graphicsLayer {
                val pageOffset = pagerScope.calculateCurrentOffsetForPage(page).absoluteValue

                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
            .fillMaxSize()
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = imageList[page]),
            contentDescription = "bannerImage",
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize()
        )
    }
}