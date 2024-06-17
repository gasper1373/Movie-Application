package com.example.movieapplication.util.desingSystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movieapplication.main.domain.models.Genre

@Composable
fun GenresProvider(
    genre_ids: List<Int>,
    allGenres: List<Genre>,
): String {
    var genres = ""
    for (i in allGenres.indices) {
        for (j in genre_ids.indices) {
            if (allGenres[i].id == genre_ids[j]) {
                genres += "${allGenres[i].name} - "
            }
        }
    }
    return genres.dropLastWhile { it == ' ' || it == '-' }
}