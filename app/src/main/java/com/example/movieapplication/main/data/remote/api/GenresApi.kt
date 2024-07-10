package com.example.movieapplication.main.data.remote.api

import com.example.movieapplication.BuildConfig.*
import com.example.movieapplication.main.data.remote.dto.GenresListDto
import com.google.android.datatransport.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GenresApi {

    @GET("genre/{type}/list")
    suspend fun getGenresList(
        @Path("type") genre: String,
        @Query("api_key") apiKey: String = api_key,
    ) : GenresListDto


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val api_key = apikeySafe
    }
}
