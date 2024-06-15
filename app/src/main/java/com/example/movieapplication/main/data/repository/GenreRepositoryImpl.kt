package com.example.movieapplication.main.data.repository

import android.app.Application
import coil.network.HttpException
import com.example.movieapplication.R
import com.example.movieapplication.main.data.local.genres.GenreEntity
import com.example.movieapplication.main.data.local.genres.GenresDatabase
import com.example.movieapplication.main.data.remote.api.GenresApi
import com.example.movieapplication.main.domain.models.Genre
import com.example.movieapplication.main.domain.repository.GenreRepository
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genresApi: GenresApi,
    private val application: Application,
    genresDb: GenresDatabase,
) : GenreRepository {
    private val genresDao = genresDb.genreDao

    override suspend fun getGenres(
        fetchFromRemote: Boolean,
        type: String,
        apiKey: String
    ): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading(true))
            val genresEntity = genresDao.getGenres(type)
            if (genresEntity.isNotEmpty() && !fetchFromRemote) {
                emit(Resource.Success(
                    genresEntity.map { genresEntity ->
                        Genre(
                            id = genresEntity.id,
                            name = genresEntity.name
                        )
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteGenreList = try {
                genresApi.getGenresList(type, apiKey).genres
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_load_genres)))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_load_genres)))
                emit(Resource.Loading(false))
                return@flow
            }
            remoteGenreList.let {
                genresDao.insertGenres(remoteGenreList.map { remoteGenre ->
                    GenreEntity(
                        id = remoteGenre.id,
                        name = remoteGenre.name,
                        type = type
                    )
                })
                emit(Resource.Success(remoteGenreList))
                emit(Resource.Loading(false))
            }
        }
    }
}