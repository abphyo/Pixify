package com.biho.home

import android.app.Activity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.biho.home.core.HomeTopAppBar
import com.biho.home.core.PostsGrid
import com.biho.home.state.PostsFeedUiState
import com.biho.pixify.core.model.danbooru.model.post.Post
import com.biho.resources.R
import com.biho.resources.theme.LocalScrollToTopChannel
import com.biho.resources.theme.LocalWindowSizeInfo
import com.biho.resources.theme.WindowSize
import com.biho.ui.model.LazyGrids
import com.biho.ui.model.PixiMenuItem
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPostItemClick: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    isRouteFirstEntry: Boolean,
    isRefreshing: Boolean,
    getPosts: () -> Unit,
    refreshGetPosts: () -> Unit,
    canLoadMore: Boolean,
    postsState: PostsFeedUiState
) {
    val context = LocalContext.current
    val scrollToTopChannel = LocalScrollToTopChannel.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()
    val lazyGridState = rememberLazyStaggeredGridState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = refreshGetPosts
    )
    val isScrollIndexZero by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex == 0 && lazyGridState.firstVisibleItemScrollOffset == 1
        }
    }

    val isLastScrollIndexVisible by remember {
        derivedStateOf {
            lazyGridState
                .layoutInfo
                .visibleItemsInfo
                .filter { it.key == "more" }
                .size == 1
        }
    }

    LaunchedEffect(key1 = isLastScrollIndexVisible) {
        if (isLastScrollIndexVisible) getPosts()
    }

    LaunchedEffect(key1 = isScrollIndexZero) {
        if (isScrollIndexZero) refreshGetPosts()
    }

    LaunchedEffect(key1 = scrollToTopChannel) {
        scrollToTopChannel.consumeEach {
            scope.launch {
                lazyGridState.animateScrollToItem(index = 0, scrollOffset = -1)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (LocalWindowSizeInfo.current.widthSize) {
                is WindowSize.Compact -> HomeTopAppBar(
                    isRouteFirstEntry = isRouteFirstEntry,
                    onNavigateBack = onNavigateBack,
                    onRefresh = {
                        scope.launch {
                            refreshGetPosts()
                            lazyGridState.animateScrollToItem(index = 0, scrollOffset = -1)
                        }
                    },
                    onExit = {
                        (context as Activity).finishAffinity()
                    },
                    scrollBehavior = scrollBehavior
                )

                is WindowSize.Medium -> {}
                is WindowSize.Expanded -> {}
            }
        }
    ) { paddingValues ->
        Crossfade(
            targetState = postsState,
            label = ""
        ) { target ->
            when (target) {
                is PostsFeedUiState.Success ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                    ) {
                        PostsGrid(
                            modifier = Modifier.fillMaxSize(),
                            type = LazyGrids.Vertical,
                            posts = target.data,
                            canLoadMore = canLoadMore,
                            gridState = lazyGridState,
                            contentPadding = paddingValues,
                            onItemClick = onPostItemClick,
                            bottomSheetItems = bottomSheetItems()
                        )
                        PullRefreshIndicator(
                            refreshing = isRefreshing,
                            state = pullRefreshState,
                            modifier = Modifier
                                .padding(paddingValues)
                                .align(Alignment.TopCenter)
                        )
                    }

                is PostsFeedUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Loading...")
                    }
                }

                is PostsFeedUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error...${target.error}")
                    }
                }
            }
        }
    }
}

fun bottomSheetItems() = { post: Post ->
    listOf(
        PixiMenuItem(
            text = {
                Text(text = stringResource(id = R.string.favourites))
            },
            enabled = false,
            onClick = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.favorite),
                    contentDescription = stringResource(
                        id = R.string.icon
                    )
                )
            },
        ),
        PixiMenuItem(
            text = {
                Text(text = stringResource(id = R.string.download))
            },
            enabled = true,
            onClick = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = stringResource(
                        id = R.string.icon
                    )
                )
            },
        ),
        PixiMenuItem(
            text = {
                Text(text = stringResource(id = R.string.share))
            },
            enabled = false,
            onClick = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.share_filled),
                    contentDescription = stringResource(
                        id = R.string.icon
                    )
                )
            },
        )
    )
}