package com.example.movieapplication.search.presentation

import androidx.room.Query
import com.example.movieapplication.main.domain.models.Media

data class SearchScreenState(
    val searchPage : Int =1 ,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val searchList:List<Media> = emptyList(),
    val isFocused: Boolean = false
)