package com.example.movieapplication.media_details.data.repository

import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.media_details.domain.models.Cast
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow

interface ExtraDetailsRepository {

    suspend fun getSimilarMediaList(
        isRefresh: Boolean,
        type: String,
        id: Int,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>>

    suspend fun getCastList(
        isRefresh :Boolean,
        id:Int,
        apiKey:String
    ):Flow<Resource<Cast>>

    suspend fun getVideoList(
        isRefresh: Boolean,
        id : Int,
        apiKey: String
    ): Flow<Resource<List<String>>>
}
