package com.biho.product.animations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun shimmerAnimation(): Brush {

    val colors = listOf(
        Color.DarkGray.copy(alpha = 0.6f),
        Color.DarkGray.copy(alpha = 0.2f),
        Color.DarkGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "Shimmer")
    val transitionAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        label = "ShimmerAnim"
    )

    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = Offset(x = transitionAnim.value, y = transitionAnim.value)
    )

}