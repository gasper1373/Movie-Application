package com.example.movieapplication.main.data.local.media

import androidx.room.Query
import androidx.room.Upsert

interface MediaDao {
    @Upsert
    suspend fun upsertMediaList(mediaEntities: List<MediaEntity>)

    @Upsert
    suspend fun upsertMediaItem(mediaItem: MediaEntity)

    @Query("DELETE FROM mediaEntity WHERE media_type = :mediaType AND category = :category")
    suspend fun deleteMediaByTypeAndCategory(mediaType: String, category: String)

    @Query("SELECT * FROM mediaentity WHERE id = :id")
    suspend fun getMediaById(id: Int): MediaEntity

    @Query("SELECT * FROM mediaentity WHERE media_type = :mediaType and category = :category")
    suspend fun getMediaListByTypeAndCategory(
        mediaType: String, category: String,
    ): List<MediaEntity>

    @Query("DELETE FROM mediaentity where category =:category")
    suspend fun deleteTrendingMediaList(category: String)

    @Query("SELECT * FROM MEDIAENTITY WHERE category = :category")
    suspend fun getTrendingMediaList(category: String): List<MediaEntity>
}