package com.example.movieapplication.main.data.mappers

import com.example.movieapplication.main.data.local.media.MediaEntity
import com.example.movieapplication.main.data.remote.dto.MediaDto
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.util.Constants

/**
fun MediaEntity.toMedia(
    type: String,
    category: String
): Media {
    return Media(
        backdrop_path = backdrop_path ?: Constants.unavailable,
        original_language = original_language ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        poster_path = poster_path ?: Constants.unavailable,
        release_date = release_date ?: first_air_date ?: Constants.unavailable,
        title = title ?: Constants.unavailable,
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        genre_ids = try {
            genre_ids?.split(",")!!.map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },
        id = id ?: 1,
        adult = adult ?: false,
        media_type = type,
        origin_country = try {
            origin_country?.split(",")!!.map { it }
        } catch (e: Exception) {
            listOf("-1", "-2")
        },
        original_title = original_title ?: original_name ?: Constants.unavailable,
        category = category,
        runtime = runtime ?: 0,
        status = status ?: "",
        tagline = tagline ?: "",
        videos = try {
            videos?.split(",")?.map { it }
        } catch (e: Exception) {
            listOf("-1", "-2")
        },
        similarMediaList = try {
            similarMediaList?.split(",")!!.map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },
    )
}

fun MediaDto.toMediaEntity(
    type: String,
    category: String,
): MediaEntity {
    return MediaEntity(
        backdrop_path = backdrop_path ?: Constants.unavailable,
        original_language = original_language ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        poster_path = poster_path ?: Constants.unavailable,
        release_date = release_date ?: "-1,-2",
        title = title ?: name ?: Constants.unavailable,
        original_name = original_name ?: Constants.unavailable,
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        id = id ?: 1,
        adult = adult ?: false,
        media_type = type,
        category = category,
        origin_country = try {
            origin_country?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        original_title = original_title ?: original_name ?: Constants.unavailable,
        videos = try {
            videos?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        similarMediaList = try {
            similarMediaList?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        first_air_date = first_air_date ?: "",
        video = video ?: false,

        status = "",
        runtime = 0,
        tagline = "",
    )
}


fun MediaDto.toMedia(
    type: String,
    category: String,
): Media {
    return Media(
        backdrop_path = backdrop_path ?: Constants.unavailable,
        origin_country = original_language ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        poster_path = poster_path ?: Constants.unavailable,
        release_date = release_date ?: Constants.unavailable,
        title = title ?: name ?: Constants.unavailable,
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        genre_ids = genre_ids ?: emptyList(),
        id = id ?: 1,
        adult = adult ?: false,
        media_type = type,
        category = category,
        originCountry = origin_country ?: emptyList(),
        originalTitle = original_title ?: original_name ?: Constants.unavailable,
        runtime = null,
        status = null,
        tagline = null,
        videos = videos,
        similarMediaList = similarMediaList ?: emptyList()
    )
}

fun Media.toMediaEntity(): MediaEntity {
    return MediaEntity(
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        popularity = popularity,
        vote_count = vote_count,
        genre_ids = try {
            genre_ids.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        id = id,
        adult = adult,
        media_type = media_type,
        origin_country = try {
            origin_country.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        original_title = original_title,
        category = category,
        videos = try {
            videos?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        similarMediaList = try {
            similarMediaList.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        video = false,
        first_air_date = release_date,
        originalName = original_title,

        status = status ?: "",
        runtime = runtime ?: 0,
        tagline = tagline ?: ""
    )
} **/