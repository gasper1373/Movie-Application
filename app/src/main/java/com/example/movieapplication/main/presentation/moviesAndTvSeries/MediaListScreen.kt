package com.example.movieapplication.main.presentation.moviesAndTvSeries

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapplication.main.presentation.main.MainUiEvents
import com.example.movieapplication.main.presentation.main.MainUiState
import com.example.movieapplication.main.presentation.main.MainViewModel
import com.example.movieapplication.ui.theme.MovieApplicationTheme
import com.example.movieapplication.util.Constants.recommendedListScreen
import com.example.movieapplication.util.Constants.topRatedAllListScreen
import com.example.movieapplication.util.Constants.trendingAllListScreen
import com.example.movieapplication.util.Constants.tvSeriesScreen
import com.example.movieapplication.util.desingSystem.MediaItem
import com.example.movieapplication.util.desingSystem.NonFocusedTopBar
import com.example.movieapplication.util.desingSystem.ShimmerEffect
import com.example.movieapplication.util.desingSystem.header
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt

//@Composable
//fun MediaListScreenRot(
//    //navController: NavController,
//    viewModel: MainViewModel = hiltViewModel(),
//) {
//    MediaListScreen(
//        state = viewModel.mainUiState,
//        onAction = viewModel::onAction
//    )
//
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MediaListScreen(
    state: MainUiState,
    selectedItem: MutableStateFlow<Int>,
    onNavigateToMediaHome: () -> Unit,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    onEvent: (MainUiEvents) -> Unit,
    onClick: () -> Unit,
    getType: () -> String?,
    getTitle: (String?) -> String,
    modifier: Modifier = Modifier,
) {

    val toolbarHeight = with(LocalDensity.current) { 24.dp.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolbarHeight, 0f)
                return Offset.Zero
            }
        }
    }
    BackHandler(enabled = true) {
        selectedItem.value = 0
        onNavigateToMediaHome()
    }
    val type = remember {
        getType()
    }
    val mediaList = when (type) {
        trendingAllListScreen -> state.trendingAllList
        topRatedAllListScreen -> state.topRatedAllList
        recommendedListScreen -> state.recommendedAllList
        tvSeriesScreen -> state.tvSeriesList
        else -> state.popularMoviesList
    }

    val title = getTitle(type)

    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        if (mediaList.isEmpty()) {
            ShimmerEffect(
                title = title,
                radius = 24
            )
        } else {
            val listState = rememberLazyGridState()
            LazyVerticalGrid(
                state = listState,
                contentPadding = PaddingValues(top = 24.dp),
                columns = GridCells.Adaptive(190.dp)
            ) {
                header {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = 16.dp,
                                horizontal = 32.dp
                            ),
                        text = title,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp
                    )
                }
                items(mediaList.size) { i ->
                    MediaItem(
                        media = mediaList[i],
                        onClick = { /*TODO*/ },
                        mainUiState = state,
                        onEvents = {/*TODO*/ }
                    )
                    if (i >= mediaList.size - 1 && !state.isLoading) {
                        if (type != null) {
                            onEvent(MainUiEvents.OnPaginate(type = type))
                        }
                    }
                }
            }
        }

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }
        PullToRefreshContainer(
            state = pullToRefreshState,
            Modifier
                .align(Alignment.Center)
                .padding(top = (16.dp))
        )
        NonFocusedTopBar(
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt(),
            onClick = {/*TODO*/ })
    }


}


//@Preview
//@Composable
//private fun MediaListScreenPreview() {
//    MovieApplicationTheme {
//        MediaListScreen(
//            state = (),
//            onAction = {}
//        )
//    }
//}