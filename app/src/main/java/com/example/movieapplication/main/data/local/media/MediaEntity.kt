package com.example.movieapplication.main.data.local.media

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaEntity(
    @PrimaryKey
    val id: Int,

    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: String,
    var media_type: String,
    var origin_country: String,
    val original_language: String,
    val original_name: String,
    val original_title: String,
    val overview: String,
    val name: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var category: String,
    var videos: String,
    var similarMediaList: String,
    var runtime: Int,
    var tagline : String,
    var status : String,
)