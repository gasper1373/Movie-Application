package com.example.movieapplication.main.data.mappers


import com.example.movieapplication.main.data.local.media.MediaEntity
import com.example.movieapplication.main.data.remote.dto.MediaDto
import com.example.movieapplication.main.domain.models.Media
import com.example.movieapplication.util.Constants


fun MediaEntity.toMedia(
    type: String,
    category: String,
): Media {
    return Media(
        backdrop_path = backdrop_path ?: Constants.unavailable,
        original_language = original_language ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        poster_path = poster_path ?: Constants.unavailable,
        release_date = release_date ?: Constants.unavailable,
        title = title ?: Constants.unavailable,
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        genre_ids = genre_ids?.split(",")?.mapNotNull { it.toIntOrNull() } ?: listOf(-1, -2),
        id = id ?: 1,
        adult = adult ?: false,
        media_type = type,
        origin_country = origin_country?.split(",") ?: listOf("-1", "-2"),
        original_title = original_title ?: original_name ?: Constants.unavailable,
        category = category,
        runtime = runtime ?: 0,
        status = status ?: "",
        tagline = tagline ?: "",
        videos = videos.split(",") ?: listOf("-1", "-2"),
        similarMediaList = similarMediaList.split(",").mapNotNull { it.toIntOrNull() }
            ?: listOf(-1, -2),
        first_air_date = first_air_date ?: "",
        name = name ?: "",
        video = video ?: false,
        original_name = original_name ?: ""
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
        genre_ids = genre_ids?.joinToString(",") ?: "-1,-2",
        id = id ?: 1,
        adult = adult ?: false,
        media_type = type,
        category = category,
        origin_country = origin_country?.joinToString(",") ?: "-1,-2",
        original_title = original_title ?: original_name ?: Constants.unavailable,
        videos = videos?.joinToString(",") ?: "-1,-2",
        similarMediaList = similarMediaList?.joinToString(",") ?: "-1,-2",
        first_air_date = first_air_date ?: "",
        video = video ?: false,
        status = "",
        runtime = 0,
        tagline = "",
        name = name ?: ""
    )
}


fun MediaDto.toMedia(
    type: String,
    category: String,
): Media {
    return Media(
        backdrop_path = backdrop_path ?: Constants.unavailable,
        original_language = original_language ?: Constants.unavailable,
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
        origin_country = origin_country ?: emptyList(),
        original_title = original_title ?: Constants.unavailable,
        runtime = null,
        status = null,
        tagline = null,
        videos = videos,
        similarMediaList = similarMediaList ?: emptyList(),
        first_air_date = first_air_date ?: "",
        name = title ?: "",
        original_name = original_title ?: "",
        video = video ?: false
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
        genre_ids = genre_ids.joinToString(","),
        id = id,
        adult = adult,
        media_type = media_type,
        origin_country = origin_country.joinToString(","),
        original_title = original_title,
        category = category,
        videos = videos?.joinToString(",") ?: "-1,-2",
        similarMediaList = similarMediaList.joinToString(","),
        video = false,
        first_air_date = release_date,
        original_name = original_title,
        status = status ?: "",
        runtime = runtime ?: 0,
        tagline = tagline ?: "",
        name = name ?: ""
    )
}
