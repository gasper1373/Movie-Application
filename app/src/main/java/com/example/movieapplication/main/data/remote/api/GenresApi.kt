package com.example.movieapplication.main.data.remote.api

import com.example.movieapplication.main.data.remote.dto.GenresListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GenresApi {

    @GET("genre/{type}/list")
    suspend fun getGenresList(
        @Path("type") genre: String,
        @Query("api_key") apiKey: String = API_KEY,
    ) : GenresListDto


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "b81a0d24730803c18bf87f19996d35ba"
    }
}
