@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapplication.main.presentation.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.movieapplication.R
import com.example.movieapplication.main.presentation.main.MainUiEvents
import com.example.movieapplication.main.presentation.main.MainUiState
import com.example.movieapplication.util.Constants.recommendedListScreen
import com.example.movieapplication.util.Constants.topRatedAllListScreen
import com.example.movieapplication.util.Constants.trendingAllListScreen
import com.example.movieapplication.util.Constants.tvSeriesScreen
import com.example.movieapplication.util.desingSystem.AutoSwipe
import com.example.movieapplication.util.desingSystem.PullToRefreshLazyColumn
import com.example.movieapplication.util.desingSystem.ShouldShowMediaHomeScreenSectionOrShimmer
import com.example.movieapplication.util.desingSystem.shimmerEffect

@Composable
fun MediaHomeScreen(
    navController: NavController,
    bottomBarNavController: NavHostController,
    state: MainUiState,
    onClick: () -> Unit,
    onEvent: (MainUiEvents) -> Unit,
    refreshing: Boolean,
    onRefresh: (String) -> Unit,
) {
    val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val context = LocalContext.current
    BackHandler(
        enabled = true
    ) {
        (context as Activity).finish()
    }
    val isRefreshing = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PullToRefreshLazyColumn(
            items = listOf(
                trendingAllListScreen to state.trendingAllList,
                "special" to state.specialList, // Special handling
                tvSeriesScreen to state.tvSeriesList,
                topRatedAllListScreen to state.topRatedAllList,
                recommendedListScreen to state.recommendedAllList,
            ),
            content = { item ->
                when (item.first) {
                    trendingAllListScreen -> {
                        ShouldShowMediaHomeScreenSectionOrShimmer(
                            type = trendingAllListScreen,
                            showShimmer = state.trendingAllList.isEmpty(),
                            mainUiState = state,
                            onClick = { /*TODO*/ },
                        )
                    }

                    tvSeriesScreen -> {
                        ShouldShowMediaHomeScreenSectionOrShimmer(
                            type = tvSeriesScreen,
                            showShimmer = state.tvSeriesList.isEmpty(),
                            mainUiState = state,
                            onClick = { /*TODO*/ },
                        )
                    }

                    topRatedAllListScreen -> {
                        ShouldShowMediaHomeScreenSectionOrShimmer(
                            type = topRatedAllListScreen,
                            showShimmer = state.topRatedAllList.isEmpty(),
                            mainUiState = state,
                            onClick = { /*TODO*/ },
                        )
                    }

                    recommendedListScreen -> {
                        ShouldShowMediaHomeScreenSectionOrShimmer(
                            type = recommendedListScreen,
                            showShimmer = state.recommendedAllList.isEmpty(),
                            mainUiState = state,
                            onClick = { /*TODO*/ },
                        )
                    }

                    "special" -> {
                        if (state.specialList.isEmpty()) {
                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = stringResource(id = R.string.special),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 20.sp
                            )
                            Box(
                                modifier = Modifier
                                    .height(220.dp)
                                    .fillMaxWidth(0.9f)
                                    .padding(
                                        top = 20.dp,
                                        bottom = 12.dp
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                                    .shimmerEffect(false)
                            )
                        } else {
                            AutoSwipe(
                                type = stringResource(id = R.string.special),
                                onClick = { /*TODO*/ },
                                mainUiState = state
                            )
                        }
                    }
                }
            },
            isRefreshing = refreshing,
            onRefresh = {
                onRefresh("")
            },

        )
    }

}