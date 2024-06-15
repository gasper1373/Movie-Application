package com.example.movieapplication.search.domain.repository

import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository{
    suspend fun getSearchList(
        forceFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Media>>>
}