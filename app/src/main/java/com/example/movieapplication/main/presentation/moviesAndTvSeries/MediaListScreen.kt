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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.movieapplication.R
import com.example.movieapplication.main.presentation.main.MainUiEvents
import com.example.movieapplication.main.presentation.main.MainUiState
import com.example.movieapplication.util.Constants.popularScreen
import com.example.movieapplication.util.Constants.recommendedListScreen
import com.example.movieapplication.util.Constants.topRatedAllListScreen
import com.example.movieapplication.util.Constants.trendingAllListScreen
import com.example.movieapplication.util.Constants.tvSeriesScreen
import com.example.movieapplication.util.desingSystem.ListShimmerEffect
import com.example.movieapplication.util.desingSystem.MediaItem
import com.example.movieapplication.util.desingSystem.PullToRefreshLazyGrid
import com.example.movieapplication.util.desingSystem.header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaListScreen(
    state: MainUiState,
    selectedItem: MutableState<Int>,
    navController: NavController,
    bottomBarNavController: NavHostController,
    navBackStackEntry: NavBackStackEntry,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    onEvent: (MainUiEvents) -> Unit,
) {
    BackHandler(enabled = true) {
        selectedItem.value = 0
        TODO()
    }
    val type = remember {
        navBackStackEntry.arguments?.getString("type")
    }
    val mediaList = when (type) {
        trendingAllListScreen -> state.trendingAllList
        topRatedAllListScreen -> state.topRatedAllList
        recommendedListScreen -> state.recommendedAllList
        tvSeriesScreen -> state.tvSeriesList
        else -> state.popularMoviesList
    }

    val title = when (type) {
        trendingAllListScreen -> stringResource(id = R.string.trending)
        topRatedAllListScreen -> stringResource(id = R.string.top_rated)
        tvSeriesScreen -> stringResource(id = R.string.tv_series)
        recommendedListScreen -> stringResource(id = R.string.recommended)
        popularScreen -> stringResource(id = R.string.popular)
        else -> ""
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 18.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (mediaList.isEmpty()) {
            ListShimmerEffect(
                title = title,
            )
        } else {
            val listState = rememberLazyGridState()
            PullToRefreshLazyGrid(
                items = mediaList,
                isRefreshing = refreshing,
                onRefresh = onRefresh,
                lazyGridState = listState,
                columns = GridCells.Adaptive(190.dp),
                header = {
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
                },
                content = { item ->
                    MediaItem(
                        media = item,
                        navController = navController,
                        mainUiState = state,
                        onEvents = onEvent
                    )
                })
        }
    }
}

