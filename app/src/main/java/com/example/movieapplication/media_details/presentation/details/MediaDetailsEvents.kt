package com.example.movieapplication.media_details.presentation.details

import com.example.movieapplication.main.domain.models.Genre
import com.example.movieapplication.main.presentation.main.MainUiEvents

sealed class MediaDetailsEvents {
    data class SetDataAndLoad(
        val moviesGenresList: List<Genre>,
        val tvGenresList: List<Genre>,
        val id: Int,
        val type: String,
        val category: String,
    ) : MediaDetailsEvents()

    object Refresh : MediaDetailsEvents()
    object NavigateToVideo : MediaDetailsEvents()
    data class onRefresh(val type: String) : MediaDetailsEvents()
}