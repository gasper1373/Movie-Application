package com.example.movieapplication.search.presentation

import com.example.movieapplication.main.domain.models.Media

sealed class SearchScreenUiEvent{
    data class Refresh(val type: String): SearchScreenUiEvent()
    data class OnPaginate(val type:String):SearchScreenUiEvent()
    data class OnSearchQueryChanged(val query:String): SearchScreenUiEvent()
    data class OnSearchedItemClick(val media: Media):SearchScreenUiEvent()
}