@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapplication.main.presentation.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.movieapplication.util.desingSystem.ShouldShowMediaHomeScreenSectionOrShimmer
import com.example.movieapplication.util.desingSystem.shimmerEffect

@Composable
fun MediaHomeScreen(
    navController: NavController,
    bottomBarNavController: NavHostController,
    state: MainUiState,
    onClick: (Int) -> Unit,
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
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 74.dp),
        ) {
            ShouldShowMediaHomeScreenSectionOrShimmer(
                type = trendingAllListScreen,
                showShimmer = state.trendingAllList.isEmpty(),
                onClick = {},
                mainUiState = state
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
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
                        .clip(RoundedCornerShape(16))
                        .shimmerEffect(false)
                        .align(CenterHorizontally)
                )
            } else {
                AutoSwipe(
                    type = stringResource(id = R.string.special),
                    onClick = {},
                    mainUiState = state
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            ShouldShowMediaHomeScreenSectionOrShimmer(
                type = tvSeriesScreen,
                showShimmer = state.tvSeriesList.isEmpty(),
                onClick = {},
                mainUiState = state
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            ShouldShowMediaHomeScreenSectionOrShimmer(
                type = topRatedAllListScreen,
                showShimmer = state.topRatedAllList.isEmpty(),
                onClick = {},
                mainUiState = state
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            ShouldShowMediaHomeScreenSectionOrShimmer(
                type = recommendedListScreen,
                showShimmer = state.recommendedAllList.isEmpty(),
                onClick = {},
                mainUiState = state
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}