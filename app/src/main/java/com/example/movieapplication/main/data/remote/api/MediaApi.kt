package com.example.movieapplication.main.data.remote.api

import com.example.movieapplication.BuildConfig
import com.example.movieapplication.main.data.remote.dto.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaApi {

    @GET("{type}/{category}")
    suspend fun getMoviesAndTvSeriesList(
        @Path("type") type: String,
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = api_key,
    ): MediaListDto

    @GET("trending/{type}/{time}")
    suspend fun getTrendingList(
        @Path("type") type: String,
        @Path("time") time: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = api_key,
    ): MediaListDto


    @GET("search/multi")
    suspend fun getSearchList(
        @Path("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = api_key,
    ) : MediaListDto


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val api_key = BuildConfig.apikeySafe
    }
}