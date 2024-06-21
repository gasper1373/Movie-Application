package com.example.movieapplication.util.desingSystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapplication.R
import com.example.movieapplication.main.presentation.main.MainUiState
import com.example.movieapplication.util.Constants

@Composable
fun ShouldShowMediaHomeScreenSectionOrShimmer(
    type: String,
    showShimmer: Boolean,
    mainUiState: MainUiState,
    onClick: () -> Unit,
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

    if (showShimmer) {
        ShowHomeShimmer(
            title = title,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp,
                    bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    } else {
//        MediaHomeScreenSection(
//            //TODO()
//        )
    }
}

@Composable
fun ShowHomeShimmer(
    title: String,
    paddingEnd: Dp,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 22.dp,
            ),
            fontWeight = FontWeight.Bold,
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp
        )

        LazyRow {
            items(10) {
                Box(
                    modifier = modifier
                        .padding(
                            end = if (it == 9) paddingEnd else 0.dp
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .shimmerEffect(false)
                )
            }
        }
    }
}