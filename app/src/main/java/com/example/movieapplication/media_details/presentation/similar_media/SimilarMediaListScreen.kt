package com.example.movieapplication.media_details.presentation.similar_media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapplication.R
import com.example.movieapplication.media_details.presentation.details.MediaDetailsScreenState
import com.example.movieapplication.media_details.presentation.details.components.SimilarMediaItem
import com.example.movieapplication.util.desingSystem.ShimmerEffect
import com.example.movieapplication.util.desingSystem.header

@Composable
fun SimilarMediaScreen(
    mediaDetailsScreenState: MediaDetailsScreenState,
    name: String,
) {
    val title = stringResource(R.string.similar_to, name)

    val mediaList = mediaDetailsScreenState.similarMediaList

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        if (mediaList.isEmpty()) {
            ShimmerEffect(
                title = title,
                radius = 8
            )
        } else {
            val listState = rememberLazyGridState()

            LazyVerticalGrid(
                state = listState,
                contentPadding = PaddingValues(top = 8.dp),
                columns = GridCells.Adaptive(190.dp)
            ) {
                header {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    vertical = 16.dp,
                                    horizontal = 22.dp
                                ),
                            textAlign = TextAlign.Center,
                            text = title,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 20.sp
                        )
                    }
                }
                items(mediaList.size) { i ->
                    SimilarMediaItem(
                        media = mediaList[i],
                        mediaDetailsScreenState = mediaDetailsScreenState
                    )
                }
            }
        }
    }
}