package com.example.movieapplication.util

import kotlinx.serialization.Serializable
import okhttp3.Route

object Route {
    const val SEARCH_SCREEN = "search_screen"
    const val MEDIA_MAIN_SCREEN = "media_main_screen"
    const val SIMILAR_MEDIA_LIST_SCREEN = "similar_media_list_screen"
    const val MEDIA_DETAILS_SCREEN = "media_detail_screen"
    const val WATCH_VIDEO_SCREEN = "watch_video_screen"
}

object BottomNavRoute {
    const val MEDIA_HOME_SCREEN = "media_home_screen"
    const val MEDIA_LIST_SCREEN = "media_list_screen"
}