package com.example.movieapplication.media_details.domain.repository

import com.example.movieapplication.main.data.local.media.MediaDatabase
import com.example.movieapplication.main.data.local.media.MediaEntity
import com.example.movieapplication.main.data.mappers.toMedia
import com.example.movieapplication.main.data.mappers.toMediaEntity
import com.example.movieapplication.main.data.remote.dto.MediaDto
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.media_details.data.remote.api.DetailsApi
import com.example.movieapplication.media_details.data.repository.ExtraDetailsRepository
import com.example.movieapplication.media_details.domain.models.Cast
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExtraDetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    mediaDb: MediaDatabase,
) : ExtraDetailsRepository {
    private val mediaDao = mediaDb.mediaDao
    override suspend fun getSimilarMediaList(
        isRefresh: Boolean,
        type: String,
        id: Int,
        page: Int,
        apiKey: String,
    ): Flow<Resource<List<Media>>> {

        return flow {

            emit(Resource.Loading(true))

            val mediaEntity = mediaDao.getMediaById(id = id)

            val doesSimilarMediaListExist =
                (mediaEntity.similarMediaList != null && mediaEntity.similarMediaList != "-1,-2")

            if (!isRefresh && doesSimilarMediaListExist) {

                try {
                    val similarMediaListIds =
                        mediaEntity.similarMediaList?.split(",")!!.map { it.toInt() }

                    val similarMediaEntityList = ArrayList<MediaEntity>()
                    for (i in similarMediaListIds.indices) {
                        similarMediaEntityList.add(mediaDao.getMediaById(similarMediaListIds[i]))
                    }
                    emit(
                        Resource.Success(
                            data = similarMediaEntityList.map {
                                it.toMedia(
                                    type = it.media_type ?: Constants.MOVIE,
                                    category = it.category ?: Constants.POPULAR
                                )
                            }
                        )
                    )
                } catch (e: Exception) {
                    emit(Resource.Error("Something went wrong."))
                }


                emit(Resource.Loading(false))
                return@flow


            }

            val remoteSimilarMediaList = fetchRemoteForSimilarMediaList(
                type = mediaEntity.media_type ?: Constants.MOVIE,
                id = id,
                page = page,
                apiKey = apiKey
            )

            if (remoteSimilarMediaList == null) {
                emit(
                    Resource.Success(
                        data = emptyList()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            remoteSimilarMediaList.let { similarMediaList ->

                val similarMediaListIntIds = ArrayList<Int>()
                for (i in similarMediaList.indices) {
                    similarMediaListIntIds.add(similarMediaList[i].id ?: -1)
                }

                mediaEntity.similarMediaList = try {
                    similarMediaListIntIds.joinToString(",")
                } catch (e: Exception) {
                    "-1,-2"
                }

                val similarMediaEntityList = remoteSimilarMediaList.map {
                    it.toMediaEntity(
                        type = it.media_type ?: Constants.MOVIE,
                        category = mediaEntity.category ?: Constants.POPULAR
                    )
                }

                mediaDao.upsertMediaList(similarMediaEntityList)

                emit(
                    Resource.Success(
                        data = similarMediaEntityList.map {
                            it.toMedia(
                                type = it.media_type ?: Constants.MOVIE,
                                category = it.category ?: Constants.POPULAR
                            )
                        }
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCastList(
        isRefresh: Boolean,
        id: Int,
        apiKey: String,
    ): Flow<Resource<Cast>> {
        TODO("Not yet implemented")
    }

    override suspend fun getVideoList(
        isRefresh: Boolean,
        id: Int,
        apiKey: String,
    ): Flow<Resource<List<String>>> {

        return flow {

            emit(Resource.Loading(true))

            val mediaEntity = mediaDao.getMediaById(id = id)

            val doVideosExist = (mediaEntity.videos != null)

            if (!isRefresh && doVideosExist) {

                try {
                    val videosIds =
                        mediaEntity.videos?.split(",")!!.map { it }

                    emit(
                        Resource.Success(data = videosIds)
                    )
                } catch (e: Exception) {
                    emit(Resource.Error("Something went wrong."))
                }

                emit(Resource.Loading(false))
                return@flow


            }

            val videosIds = fetchRemoteForVideosIds(
                type = mediaEntity.media_type ?: Constants.MOVIE,
                id = id,
                apiKey = apiKey
            )

            if (videosIds == null) {
                emit(
                    Resource.Success(
                        data = emptyList()
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            videosIds.let {
                mediaEntity.videos = try {
                    it.joinToString(",")
                } catch (e: Exception) {
                    "-1,-2"
                }

                mediaDao.upsertMediaItem(mediaEntity)

                emit(
                    Resource.Success(data = videosIds)
                )

                emit(Resource.Loading(false))

            }


        }
    }

    private suspend fun fetchRemoteForVideosIds(
        type: String,
        id: Int,
        apiKey: String,
    ): List<String>? {

        val remoteVideosIds = try {
            detailsApi.getVideosList(
                type = type,
                id = id,
                apiKey = apiKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }

        val listToReturn = remoteVideosIds?.results?.filter {
            it.site == "YouTube" && it.type == "Featurette" || it.type == "Teaser"
        }

        return listToReturn?.map {
            it.key
        }

    }


    private suspend fun fetchRemoteForSimilarMediaList(
        type: String,
        id: Int,
        page: Int,
        apiKey: String,
    ): List<MediaDto>? {

        val remoteSimilarMediaList = try {
            detailsApi.getSimilar(
                type = type,
                id = id,
                page = page,
                apiKey = apiKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }

        return remoteSimilarMediaList?.results

    }
}
