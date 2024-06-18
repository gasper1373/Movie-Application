package com.example.movieapplication.media_details.domain.usecase

import android.annotation.SuppressLint

class MinutesToReadTime(
    private val minutes: Int,
) {
    @SuppressLint("DefaultLocale")
    operator fun invoke(): String {
        val hours = minutes / 60
        val remainMinutes = minutes % 60
        return String.format(
            "%02d hr %02d min", hours, remainMinutes
        )
    }
}