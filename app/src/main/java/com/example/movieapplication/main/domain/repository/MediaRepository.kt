package com.example.movieapplication.main.domain.repository

import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun getItem(
        id: Int,
        type: String,
        category: String,
    ): Media

    suspend fun getMoviesAndTvSeriesList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>>

    suspend fun getTrendingMediaList(
        fetchFromRemote: Boolean,
        isRefresh:Boolean,
        type: String,
        time: String,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>>
}