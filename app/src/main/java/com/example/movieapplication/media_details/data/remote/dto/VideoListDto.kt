package com.example.movieapplication.media_details.data.remote.dto

data class VideoListDto(
    val id: Int,
    val results: List<VideoListDto>
)