package com.example.movieapplication.main.data.local.media

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MediaEntity::class],
    version = 1, exportSchema = false)
abstract class MediaDatabase: RoomDatabase(){
    abstract val mediaDao : MediaDao
}