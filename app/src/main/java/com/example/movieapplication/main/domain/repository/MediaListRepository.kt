package com.example.movieapplication.main.domain.repository

import com.example.movieapplication.main.domain.models.Genre
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow

interface MediaListRepository {
    suspend fun getItem(
        id: Int,
        type: String,
        category: String,
    ): Media

    suspend fun getMediaListByTypeAndCategory(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        mediaType: String,
        category: String,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>>

    suspend fun getTrendingMediaList(
        fetchFromRemote: Boolean,
        type: String,
        timeWindow: String,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>>
}