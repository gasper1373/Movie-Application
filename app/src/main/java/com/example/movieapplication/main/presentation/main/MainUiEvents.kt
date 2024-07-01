package com.example.movieapplication.main.presentation.main

import com.example.movieapplication.util.UiText

sealed class MainUiEvents {
    data class Refresh(val type: String) : MainUiEvents()
    data class OnPaginate(val type: String) : MainUiEvents()
    data class Error(val error : UiText): MainUiEvents()
    data class OnClick(val type: Int): MainUiEvents()
}