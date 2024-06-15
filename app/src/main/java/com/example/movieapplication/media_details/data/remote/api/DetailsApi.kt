package com.example.movieapplication.media_details.data.remote.api

import com.example.movieapplication.main.data.remote.dto.MediaListDto
import com.example.movieapplication.media_details.data.remote.dto.DetailsDto
import com.example.movieapplication.media_details.data.remote.dto.VideosList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApi {

    @GET("{type}/{id}")
    suspend fun getDetails(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): DetailsDto

    @GET("{type}/{id}/similar")
    suspend fun getSimilar(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): MediaListDto

    @GET("{type}/{id}/videos")
    suspend fun getVideosList(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): VideosList


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "b81a0d24730803c18bf87f19996d35ba"
    }
}