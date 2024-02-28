package com.biho.product.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_200dp
import com.biho.resources.theme.DIMENS_250dp
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeaderSlider() {
    val loginImages: List<Int> = listOf(
        R.drawable.igor_artyomenko_darsun,
        R.drawable.igor_artyomenko_jett25,
        R.drawable.igor_artyomenko_silver_fleet
    )
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { }
            ),
        contentAlignment = Alignment.Center
    ) {
        CustomHorizontalPager(
            count = loginImages.size,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(DIMENS_200dp)
        ) { page, pageScope ->
            LoginImageCard(page = page, pagerScope = pageScope, imageList = loginImages)
        }
    }
}