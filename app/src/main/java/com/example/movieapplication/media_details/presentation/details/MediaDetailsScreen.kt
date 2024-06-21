@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movieapplication.media_details.presentation.details

import android.provider.MediaStore.Video
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.movieapplication.main.data.remote.api.MediaApi
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.main.presentation.moviesAndTvSeries.InfoSection
import com.example.movieapplication.main.presentation.moviesAndTvSeries.OverviewSection
import com.example.movieapplication.main.presentation.moviesAndTvSeries.PosterSection
import com.example.movieapplication.main.presentation.moviesAndTvSeries.SimilarMediaSection
import com.example.movieapplication.main.presentation.moviesAndTvSeries.VideoSection

@Composable
fun MediaDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    media: Media,
    onClick: () -> Unit,
    state: MediaDetailsScreenState,
    onEvent: (MediaDetailsEvents) -> Unit,
) {

    val pullToRefreshState = rememberPullToRefreshState()

    val imageUrl = "${MediaApi.IMAGE_BASE_URL}${media.backdrop_path}"

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val surface = MaterialTheme.colorScheme.surface
    var averageColor by remember {
        mutableStateOf(surface)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            VideoSection(
                onClick = { /*TODO*/ },
                state = state,
                media = media,
                imageState = imagePainter.state,
                onEvent = onEvent   //TODO
            ) { color ->
                averageColor = color
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                PosterSection(media = media) {}
                Spacer(modifier = Modifier.width(12.dp))
                InfoSection(media = media, state = state)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OverviewSection(media = media)
        SimilarMediaSection(
            onClick = { /*TODO*/ },
            media = media,
            state = state
        )
        Spacer(modifier = modifier.height(16.dp))
        PullToRefreshContainer(state = pullToRefreshState)

    }
}