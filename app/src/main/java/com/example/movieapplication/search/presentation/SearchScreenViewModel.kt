package com.example.movieapplication.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.main.domain.repository.MediaRepository
import com.example.movieapplication.search.domain.repository.SearchRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchScreenViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val searchRepository: SearchRepository,
) : ViewModel() {


    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState = _searchScreenState.asStateFlow()

    private var searchJob: Job? = null
    fun onEvent(event: SearchScreenUiEvent) {
        when (event) {
            is SearchScreenUiEvent.OnSearchedItemClick -> {
            }

            is SearchScreenUiEvent.OnSearchQueryChanged -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)

                    _searchScreenState.update {
                        it.copy(
                            searchQuery = event.query,
                            searchList = emptyList()
                        )
                    }
                    //loadSearchList()
                }
            }
            else -> Unit
        }
    }
}