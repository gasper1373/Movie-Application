package com.example.movieapplication.main.domain.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Media(
    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    var media_type: String,
    var origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val original_title: String,
    val overview: String,
    val name: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val runtime: Int?,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var category: String,
    var tagline: String?,
    var status: String?,
    val videos: List<String>?,
    val similarMediaList: List<Int>,
) : Parcelable