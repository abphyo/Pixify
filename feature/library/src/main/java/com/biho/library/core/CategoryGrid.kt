package com.biho.library.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.biho.product.movable.movable
import com.biho.resources.R
import com.biho.resources.theme.DIMENS_16dp
import com.biho.resources.theme.LocalGridItemPaddingMedium

@Composable
fun CategoryGrid(
    categories: List<CategoryCardItem>,
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryComposable = categories.movable { item ->
        CategoryCard(
            title = item.text,
            icon = item.icon,
            color = item.color,
            onClick = onItemClick,
            route = item.route
        )
    }
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize().padding(horizontal = DIMENS_16dp),
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(LocalGridItemPaddingMedium.current),
        verticalItemSpacing = LocalGridItemPaddingMedium.current,
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            Text(
                text = stringResource(id = R.string.categories),
                style = MaterialTheme.typography.titleLarge
            )
        }
        items(
            items = categories,
            key = { it.text }
        ) { category ->
            categoryComposable(category)
        }
    }
}