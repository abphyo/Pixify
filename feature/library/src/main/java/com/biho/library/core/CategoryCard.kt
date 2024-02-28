package com.biho.library.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.biho.product.extensions.noRippleClickable
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.DIMENS_4dp
import com.biho.resources.theme.DIMENS_80dp
import com.biho.resources.theme.DIMENS_8dp
import com.biho.resources.theme.theming_green

@Composable
fun CategoryCard(
    title: String,
    icon: ImageVector,
    color: Brush,
    route: String,
    onClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(DIMENS_16dp),
        elevation = CardDefaults.cardElevation(DIMENS_4dp),
        modifier = Modifier
            .height(DIMENS_80dp)
            .noRippleClickable {
                onClick(route)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(DIMENS_8dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(DIMENS_4dp))
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = R.string.icon)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCategoryCard() {
    CategoryCard(
        title = stringResource(id = R.string.favourites),
        icon = Icons.Filled.Favorite,
        color = Brush.linearGradient(
            colors = listOf(
                Color.DarkGray,
                theming_green
            )
        ),
        onClick = { },
        route = ""
    )
}