package com.example.movieapplication.main.presentation.moviesAndTvSeries

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import com.example.movieapplication.R
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.media_details.presentation.details.MediaDetailsEvents
import com.example.movieapplication.media_details.presentation.details.MediaDetailsScreenState
import com.example.movieapplication.media_details.presentation.details.components.MovieImage
import com.example.movieapplication.util.Route

@Composable
fun VideoSection(
    navController: NavController,
    state: MediaDetailsScreenState,
    media: Media,
    imageState: AsyncImagePainter.State,
    onEvent: (MediaDetailsEvents) -> Unit,
    onImageLoaded: (color: Color) -> Unit,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                if (state.videosList.isNotEmpty()) {
                    onEvent(MediaDetailsEvents.NavigateToVideo)
                    navController.navigate(
                        "${Route.WATCH_VIDEO_SCREEN}?videoId=${state.videoId}"
                    )
                } else {
                    Toast
                        .makeText(
                            context, context.getString(R.string.no_video_at_this_momment),
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            },
        shape = RoundedCornerShape(0),
        elevation = CardDefaults.elevatedCardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            MovieImage(
                imageState = imageState,
                description = media.title,
                noImageId = null
            ) { color ->
                onImageLoaded(color)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .size(50.dp)
                    .alpha(0.7f)
                    .background(Color.LightGray)
            )
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = stringResource(id = R.string.watch_trailer),
                tint = Color.Black,
                modifier = Modifier.size(35.dp)

            )
        }
    }
}