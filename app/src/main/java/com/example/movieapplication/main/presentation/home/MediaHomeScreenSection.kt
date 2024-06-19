package com.example.movieapplication.main.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapplication.R
import com.example.movieapplication.main.presentation.main.MainUiState
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.desingSystem.Item

@Composable
fun MediaHomeScreenSection(
    type: String,
    onClick: () -> Unit,
    onBottomClick: () -> Unit,
    state: MainUiState,
) {
    val title = when (type) {
        Constants.trendingAllListScreen -> {
            stringResource(id = R.string.trending)
        }

        Constants.tvSeriesScreen -> {
            stringResource(id = R.string.tv_series)
        }

        Constants.recommendedListScreen -> {
            stringResource(id = R.string.recommended)
        }

        else -> {
            stringResource(id = R.string.top_rated)
        }
    }
    val mediaList = when (type) {
        Constants.trendingAllListScreen -> {
            state.trendingAllList.take(10)
        }

        Constants.tvSeriesScreen -> {
            state.tvSeriesList.take(10)
        }

        Constants.recommendedListScreen -> {
            state.recommendedAllList.take(10)
        }

        else -> {
            state.topRatedAllList.take(10)
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier
                    .alpha(0.7f)
                    .clickable {
                        /*TODO*/
                    },
                text = stringResource(id = R.string.see_all),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 14.sp
            )
        }
        LazyRow {
            items(mediaList.size) {
                var paddingEnd = 0.dp
                if (it == mediaList.size - 1) {
                    paddingEnd = 16.dp
                }
                Item(
                    media = mediaList[it],
                    onMediaClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                        .padding(start = 16.dp, end = paddingEnd)
                )
            }
        }
    }

}