package com.example.movieapplication.search.data.repository

import android.app.Application
import com.example.movieapplication.main.data.local.media.MediaDatabase
import com.example.movieapplication.main.data.mappers.toMedia
import com.example.movieapplication.main.data.remote.api.MediaApi
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.search.domain.repository.SearchRepository
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val mediaApi: MediaApi,
    mediaDb: MediaDatabase,
) : SearchRepository {
        override suspend fun getSearchList(
        forceFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteMediaList = try {
                mediaApi.getSearchList(query, page, apiKey).results.map { media ->
                    media.toMedia(
                        type = media.media_type ?: Constants.unavailable,
                        category = media.category ?: Constants.unavailable
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error("Couldn't load data")
                )
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Success(remoteMediaList))
            emit(Resource.Loading(false))
        }
    }

}