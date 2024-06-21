package com.example.movieapplication.main.data.remote.api

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
        @Query("api_key") apiKey: String = API_KEY,
    ): MediaListDto

    @GET("trending/{type}/{time}")
    suspend fun getTrendingMovieListAndSeriesList(
        @Path("type") type: String,
        @Path("time") time: String = "day",
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MediaListDto


    @GET("search/multi")
    suspend fun getSearchList(
        @Path("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ) : MediaListDto


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "b81a0d24730803c18bf87f19996d35ba"
    }
}