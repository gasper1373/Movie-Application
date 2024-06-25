package com.example.movieapplication.main.data.repository

import com.example.movieapplication.main.data.local.media.MediaDatabase
import com.example.movieapplication.main.data.mappers.toMedia
import com.example.movieapplication.main.data.mappers.toMediaEntity
import com.example.movieapplication.main.data.remote.api.MediaApi
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.main.domain.repository.MediaRepository
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.Constants.TRENDING
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepositoryImpl @Inject constructor(
    private val mediaApi: MediaApi,
    mediaDb: MediaDatabase,
) : MediaRepository {
    private val mediaDao = mediaDb.mediaDao

    override suspend fun getItem(id: Int, type: String, category: String): Media {
        return mediaDao.getMediaById(id).toMedia(
            category = category,
            type = type
        )
    }

    override suspend fun getMoviesAndTvSeriesList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        mediaType: String,
        category: String,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByTypeAndCategory(mediaType, category)

            val shouldJustLoadFromCache =
                localMediaList.isNotEmpty() && !fetchFromRemote && !isRefresh
            if (shouldJustLoadFromCache) {

                emit(Resource.Success(
                    data = localMediaList.map {
                        it.toMedia(
                            type = mediaType,
                            category = category
                        )
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            var searchPage = page
            if (isRefresh) {
                mediaDao.deleteMediaByTypeAndCategory(mediaType, category)
                searchPage = 1
            }

            val remoteMediaList = try {
                mediaApi.getTrendingList(
                    mediaType, category, searchPage, apiKey
                ).results
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList.let { mediaList ->
                val media = mediaList.map {
                    it.toMedia(
                        type = mediaType,
                        category = category
                    )
                }

                val entities = mediaList.map {
                    it.toMediaEntity(
                        type = mediaType,
                        category = category,
                    )
                }

                mediaDao.upsertMediaList(entities)

                emit(
                    Resource.Success(data = media)
                )
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getTrendingMediaList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        timeWindow: String,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getTrendingMediaList(Constants.TRENDING)


            val shouldJustLoadFromCache = localMediaList.isNotEmpty() && !fetchFromRemote
            if (shouldJustLoadFromCache) {

                emit(Resource.Success(
                    data = localMediaList.map {
                        it.toMedia(
                            type = it.media_type ?: Constants.unavailable,
                            category = TRENDING
                        )
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            var searchPage = page

            if (isRefresh) {
                mediaDao.deleteTrendingMediaList(TRENDING)
                searchPage = 1
            }

            val remoteMediaList = try {
                mediaApi.getTrendingList(
                    type, timeWindow, searchPage, apiKey
                ).results
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList.let { mediaList ->

                val media = mediaList.map {
                    it.toMedia(
                        type = it.media_type ?: Constants.unavailable,
                        category = TRENDING
                    )
                }

                val entities = mediaList.map {
                    it.toMediaEntity(
                        type = it.media_type ?: Constants.unavailable,
                        category = TRENDING
                    )
                }

                mediaDao.upsertMediaList(entities)

                emit(
                    Resource.Success(data = media)
                )
                emit(Resource.Loading(false))
            }
        }

    }
}


