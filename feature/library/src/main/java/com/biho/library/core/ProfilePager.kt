package com.biho.library.core

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.lerp
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.product.composables.CustomHorizontalPager
import com.biho.product.extensions.noRippleClickable
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.DIMENS_4dp
import com.biho.resources.theme.DIMENS_8dp
import com.biho.resources.theme.theming_green
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfilePager(
    profiles: List<Profile>,
    pagerState: PagerState,
    onItemSelect: (Int?) -> Unit,
    onItemClick: (Int?) -> Unit,
    onItemEdit: (Int?) -> Unit,
    onItemDelete: (Profile) -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { }
            )
    ) {
        CustomHorizontalPager(
            pagerState = pagerState,
            count = profiles.size,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
        ) { page, pageScope ->
            val profile = profiles[page]
            Column {
                ProfileCard(
                    modifier = Modifier.padding(DIMENS_8dp),
                    page = page,
                    pagerScope = pageScope,
                    profile = profile,
                    onClick = onItemClick
                )
                Spacer(modifier = Modifier.height(DIMENS_4dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = DIMENS_8dp)
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                        .graphicsLayer {
                            val pageOffset =
                                pageScope.calculateCurrentOffsetForPage(page = page).absoluteValue

                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            alpha = lerp(
                                start = -1f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when {
                        profiles[page].isActive -> Editor(
                            onEdit = { onItemEdit(profile.roomId) },
                            onDelete = { onItemDelete(profile) },
                            isDefaultAcc = profile.roomId == 1
                        )
                        !profiles[page].isActive -> Select {
                            onItemSelect(profile.roomId)
                        }
                    }

                }
            }

        }
    }
}

@Composable
fun Select(
    onSelect: () -> Unit
) {
    Text(
        modifier = Modifier.noRippleClickable {
            onSelect()
        },
        text = stringResource(id = R.string.select),
        style = MaterialTheme.typography.bodySmall.copy(color = theming_green)
    )
}

@Composable
fun Editor(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    isDefaultAcc: Boolean
) {
    Text(
        modifier = Modifier.noRippleClickable {
            onEdit()
        },
        text = stringResource(id = R.string.edit),
        style = MaterialTheme.typography.bodySmall.copy(color = theming_green)
    )
    if (!isDefaultAcc) {
        Spacer(modifier = Modifier.width(DIMENS_16dp))
        Text(
            modifier = Modifier.noRippleClickable {
                onDelete()
            },
            text = stringResource(id = R.string.delete),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Red)
        )
    }
}