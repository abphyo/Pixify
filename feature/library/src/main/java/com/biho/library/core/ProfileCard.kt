package com.biho.library.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.util.lerp
import com.biho.pixify.core.model.danbooru.model.profile.ContentFilter
import com.biho.pixify.core.model.danbooru.model.profile.Profile
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_1dp
import com.biho.resources.theme.DIMENS_2dp
import com.biho.resources.theme.DIMENS_48dp
import com.biho.resources.theme.DIMENS_4dp
import com.biho.resources.theme.DIMENS_8dp
import com.biho.resources.theme.LocalButtonGradient
import com.biho.resources.theme.Primary
import com.biho.resources.theme.theming_green
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileCard(
    page: Int,
    pagerScope: PagerScope,
    modifier: Modifier = Modifier,
    profile: Profile,
    onClick: (Int?) -> Unit
) {
    Card(
        shape = RoundedCornerShape(DIMENS_8dp),
        colors = CardDefaults.cardColors(
            containerColor = if (profile.isActive) MaterialTheme.colorScheme.secondaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
            .clickable {
                onClick(profile.roomId)
            }
            .graphicsLayer {
                val pageOffset = pagerScope.calculateCurrentOffsetForPage(page = page).absoluteValue

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
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DIMENS_8dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.box),
                contentDescription = "profile_image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(DIMENS_48dp)
                    .border(
                        width = DIMENS_1dp,
                        brush = LocalButtonGradient.current,
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(DIMENS_8dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = profile.engine.domainName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(DIMENS_4dp))
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = buildAnnotatedString {
                        append(profile.name)
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = Primary
                            )
                        ) {
                            append(profile.level.name.uppercase())
                        }
                    }
                )

            }
            Spacer(modifier = Modifier.width(DIMENS_8dp))
            if (profile.localProfileSettings.contentFiltering == ContentFilter.Aggressive) {
                Text(
                    text = stringResource(id = R.string.safe),
                    style = MaterialTheme.typography.bodySmall
                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .align(Alignment.Top)
                        .drawBehind {
                            drawRoundRect(
                                color = theming_green,
                                cornerRadius = CornerRadius(DIMENS_2dp.toPx())
                            )
                        },
                    textAlign = TextAlign.Center,
                    lineHeight = TextUnit.Unspecified
                )
            }
        }
    }
}