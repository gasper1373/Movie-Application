package com.example.movieapplication.media_details.domain.repository

import com.example.movieapplication.main.data.local.media.MediaDatabase
import com.example.movieapplication.main.data.mappers.toMedia
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.media_details.data.remote.api.DetailsApi
import com.example.movieapplication.media_details.data.remote.dto.DetailsDto
import com.example.movieapplication.media_details.data.repository.DetailsRepository
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    mediaDb: MediaDatabase,
) : DetailsRepository {
    private val mediaDao = mediaDb.mediaDao
    override suspend fun getDetails(
        type: String,
        isRefresh: Boolean,
        id: Int,
        apiKey: String,
    ): Flow<Resource<Media>> {
        return flow {
            emit(Resource.Loading(true))
            val mediaEntity = mediaDao.getMediaById(id = id)

            val areDetailsExists = !(mediaEntity.runtime == null ||
                    mediaEntity.status == null || mediaEntity.tagline == null)
            if (!isRefresh && areDetailsExists) {
                emit(
                    Resource.Success(
                        data = mediaEntity.toMedia(
                            type = mediaEntity.media_type ?: Constants.MOVIE,
                            category = mediaEntity.category ?: Constants.POPULAR
                        )
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }
            val remotedDetails = fetchRemoteForDetails(
                type = mediaEntity.media_type ?: Constants.MOVIE,
                id = id,
                apiKey = apiKey
            )
            if (remotedDetails == null) {
                emit(
                    Resource.Success(
                        data = mediaEntity.toMedia(
                            type = mediaEntity.media_type ?: Constants.MOVIE,
                            category = mediaEntity.category ?: Constants.POPULAR
                        )
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }
            remotedDetails.let { details ->
                mediaEntity.runtime = details.runtime
                mediaEntity.tagline = details.tagline
                mediaEntity.status = details.status

                mediaDao.upsertMediaItem(mediaEntity)

                emit(
                    Resource.Success(
                        data = mediaEntity.toMedia(
                            type = mediaEntity.media_type,
                            category = mediaEntity.category
                        )
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }

    private suspend fun fetchRemoteForDetails(
        type: String,
        id: Int,
        apiKey: String,
    ): DetailsDto? {
        val remoteDetails = try {
            detailsApi.getDetails(
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

        return remoteDetails

    }
}