package com.example.movieapplication.main.data.repository

import com.example.movieapplication.main.data.local.genres.GenresDatabase
import com.example.movieapplication.main.data.remote.api.GenresApi
import com.example.movieapplication.main.domain.models.Genre
import com.example.movieapplication.main.domain.repository.GenreRepository
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genresApi: GenresApi,
    private val genresDatabase: GenresDatabase,
) : GenreRepository {
    private val nextPage: Int = 1

    override suspend fun getGenres(
        fetchFromRemote: Boolean,
        mediaType: String,
        page: Int,
    ): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading(true))

        }
    }
}