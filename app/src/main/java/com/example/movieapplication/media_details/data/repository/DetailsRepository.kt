package com.example.movieapplication.media_details.data.repository

import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    suspend fun getDetails(
        type: String,
        isRefresh: Boolean,
        id: Int,
        apiKey: String,
    ): Flow<Resource<Media>>
}