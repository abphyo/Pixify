package com.biho.home.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.product.movable.movable
import com.biho.resources.theme.LocalGridItemMinSize
import com.biho.resources.theme.LocalGridItemPaddingSmall
import com.biho.resources.theme.LocalWindowSizeInfo
import com.biho.resources.theme.WindowSize
import com.biho.ui.model.LazyGrids

@Composable
fun PostsGrid(
    type: LazyGrids,
    posts: List<Post>,
    canLoadMore: Boolean,
    gridState: LazyStaggeredGridState,
    contentPadding: PaddingValues,
    onItemClick: (Int) -> Unit,
    onItemLongClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val postComposable = posts.movable { post ->
        PostGridItem(post = post, onClick = onItemClick, onLongClick = onItemLongClick)
    }

    when (type) {

        LazyGrids.Horizontal -> LazyHorizontalStaggeredGrid(
            state = gridState,
            rows = StaggeredGridCells.Adaptive(LocalGridItemMinSize.current),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(LocalGridItemPaddingSmall.current),
            horizontalItemSpacing = LocalGridItemPaddingSmall.current,
            modifier = modifier.fillMaxSize()
        ) {
            items(
                items = posts,
                key = { it.id }
            ) { post ->
                postComposable(post)
            }
            if (canLoadMore && posts.isNotEmpty()) {
                item(
                    key = "more",
                    span = StaggeredGridItemSpan.FullLine,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        LazyGrids.Vertical -> LazyVerticalStaggeredGrid(
            state = gridState,
            columns = StaggeredGridCells.Fixed(
                when (LocalWindowSizeInfo.current.widthSize) {
                    WindowSize.Compact -> 2
                    WindowSize.Medium -> 4
                    WindowSize.Expanded -> 5
                }
            ),
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(LocalGridItemPaddingSmall.current),
            verticalItemSpacing = LocalGridItemPaddingSmall.current,
            modifier = modifier.fillMaxSize()
        ) {
            items(
                items = posts,
                key = { it.id }
            ) { post ->
                postComposable(post)
            }
            if (canLoadMore && posts.isNotEmpty()) {
                item(
                    key = "more",
                    span = StaggeredGridItemSpan.FullLine,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    ) {
                        CircularProgressIndicator()
                    }
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }
    }

}