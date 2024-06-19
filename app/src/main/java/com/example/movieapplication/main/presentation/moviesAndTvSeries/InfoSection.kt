package com.example.movieapplication.main.presentation.moviesAndTvSeries

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.media_details.presentation.details.MediaDetailsScreenState
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.Constants.unavailable
import com.example.movieapplication.util.desingSystem.GenresProvider
import com.example.movieapplication.util.desingSystem.RatingBar

@Composable
fun InfoSection(
    media: Media,
    state: MediaDetailsScreenState,
) {
    val genres = GenresProvider(
        genre_ids = media.genre_ids,
        allGenres = if (media.media_type == Constants.MOVIE) state.moviesGenresList
        else state.tvGenresList
    )

    Column {
        Spacer(modifier = Modifier.height(260.dp))

        Text(
            text = media.title,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingBar(
                modifier = Modifier,
                starsModifier = Modifier.size(18.dp),
                rating = media.vote_average.div(2)
            )
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = media.vote_average.toString().take(3),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (media.release_date != unavailable)
                    media.release_date.take(4) else "",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 0.5.dp),
                text = if (media.adult) "+18" else "-12", //TODO
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = genres,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = state.readableTime,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
            )
        }
    }
}