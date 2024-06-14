package com.example.movieapplication.main.domain.repository

import com.example.movieapplication.main.domain.models.Genre
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    suspend fun getGenres(
        fetchFromRemote: Boolean,
        mediaType: String,
        page: Int,
    ): Flow<Resource<List<Genre>>>
}