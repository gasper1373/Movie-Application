package com.example.movieapplication.main.presentation.moviesAndTvSeries

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieapplication.R
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.media_details.presentation.details.MediaDetailsScreenState
import com.example.movieapplication.util.Route
import com.example.movieapplication.util.desingSystem.Item

@Composable
fun SimilarMediaSection(
    navController: NavController,
    media: Media,
    state: MediaDetailsScreenState,
) {

    val title = stringResource(id = R.string.similar)
    val mediaList = state.smallSimilarMediaList

    if (mediaList.isNotEmpty()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 22.dp, top = 22.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )

                Text(
                    modifier = Modifier
                        .alpha(0.85f)
                        .clickable {
                            navController.navigate(
                                "${Route.MEDIA_DETAILS_SCREEN}?id=${media.id}&type=${media.media_type}&category=${media.category}"
                            )

                        },
                    text = stringResource(id = R.string.see_all),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
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
                       navController = navController ,
                        modifier = Modifier
                            .height(200.dp)
                            .width(150.dp)
                            .padding(start = 16.dp, end = paddingEnd)
                    )
                }
            }
        }
    }
}