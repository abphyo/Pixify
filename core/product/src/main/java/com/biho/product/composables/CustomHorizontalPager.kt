package com.biho.product.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_12dp
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.DIMENS_250dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomHorizontalPager(
    count: Int,
    pagerState: PagerState,
    interactionSource: MutableInteractionSource,
    modifier: Modifier,
    indicatorEnabled: Boolean = true,
    itemContent: @Composable (Int, PagerScope) -> Unit,
) {

    val isOnPressed by interactionSource.collectIsPressedAsState()
//    LaunchedEffect(key1 = Unit) {
//        val autoScrollJob = launch {
//            while (true) {
//                yield()
//                delay(2500)
//                pagerState.animateScrollToPage(
//                    page = (pagerState.currentPage + 1) % pagerState.pageCount
//                )
//            }
//        }
//        interactionSource.interactions.collectLatest {
//            when (it) {
//                is DragInteraction.Start -> autoScrollJob.cancel()
//            }
//        }
//    }

    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        HorizontalPager(
            modifier = modifier,
            count = count,
            state = pagerState,
        ) { page ->
            itemContent(page, this)
        }
        if (indicatorEnabled) {
            AnimatedVisibility(
                visible = pagerState.isScrollInProgress || isOnPressed ,
//            modifier = Modifier.animateContentSize(
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioLowBouncy,
//                    stiffness = Spring.StiffnessLow
//                )
//            ),
                exit = slideOutVertically (
                    targetOffsetY = { -it / 2 },
                    animationSpec = tween(
                        durationMillis = 2500,
                        delayMillis = 3500,
                        easing = FastOutSlowInEasing
                    )
                )
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier.padding(DIMENS_12dp)
                )
            }
        }
    }
}



@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun AutoSlidePagerPreview() {
    val imageList = listOf(
        R.drawable.img_banner1,
        R.drawable.img_banner2,
        R.drawable.img_banner3,
    )

    val interactionSource = remember {
        MutableInteractionSource()
    }

    CustomHorizontalPager(
        count = imageList.size,
        pagerState = rememberPagerState(initialPage = 0),
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIMENS_16dp)
            .height(DIMENS_250dp)
    ) { page, pagerScope ->
        LoginImageCard(page = page, imageList = imageList, pagerScope = pagerScope)
    }

}