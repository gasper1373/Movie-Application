package com.example.movieapplication.media_details.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.main.data.remote.api.GenresApi.Companion.API_KEY
import com.example.movieapplication.main.domain.repository.MediaRepository
import com.example.movieapplication.main.presentation.main.MainUiEvents
import com.example.movieapplication.media_details.data.repository.DetailsRepository
import com.example.movieapplication.media_details.data.repository.ExtraDetailsRepository
import com.example.movieapplication.media_details.domain.usecase.MinutesToReadTime
import com.example.movieapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailsViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val detailsRepository: DetailsRepository,
    private val extraDetailsRepository: ExtraDetailsRepository,
) : ViewModel() {
    private val _mediaDetailsScreenState = MutableStateFlow(MediaDetailsScreenState())
    val mediaDetailsScreenState = _mediaDetailsScreenState.asStateFlow()

    fun onEvent(events: MediaDetailsEvents) {
        when (events) {
            MediaDetailsEvents.NavigateToVideo -> {
                _mediaDetailsScreenState.update {
                    it.copy(
                        videoId = mediaDetailsScreenState.value.videosList.shuffled()[0]
                    )
                }
            }

            MediaDetailsEvents.Refresh -> {
                _mediaDetailsScreenState.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }

            is MediaDetailsEvents.SetDataAndLoad -> {
                _mediaDetailsScreenState.update {
                    it.copy(
                        moviesGenresList = events.moviesGenresList,
                        tvGenresList = events.tvGenresList
                    )
                }
                startLoad(
                    isRefresh = false,
                    id = events.id,
                    type = events.type,
                    category = events.category
                )
            }
            else -> Unit
        }
    }

    private fun startLoad(
        isRefresh: Boolean,
        id: Int = mediaDetailsScreenState.value.media?.id ?: 0,
        type: String = mediaDetailsScreenState.value.media?.media_type ?: "",
        category: String = mediaDetailsScreenState.value.media?.category ?: "",
    ) {
        loadMediaItem(
            id = id,
            type = type,
            category = category
        ) {
            loadDetails(isRefresh = isRefresh)
            loadDetails(isRefresh = isRefresh)
            loadSimilarMediaList(
                isRefresh = isRefresh
            )
            loadVideList(isRefresh = isRefresh)
        }

    }

    private fun loadVideList(isRefresh: Boolean) {
        viewModelScope.launch {
            extraDetailsRepository.getVideoList(
                id = mediaDetailsScreenState.value.media?.id ?: 0,
                isRefresh = isRefresh,
                apiKey = API_KEY
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _mediaDetailsScreenState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { videosList ->
                            _mediaDetailsScreenState.update {
                                it.copy(
                                    videosList = videosList
                                )
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun loadSimilarMediaList(isRefresh: Boolean) {
        viewModelScope.launch {
            extraDetailsRepository.getSimilarMediaList(
                isRefresh = isRefresh,
                id = mediaDetailsScreenState.value.media?.id ?: 0,
                type = mediaDetailsScreenState.value.media?.media_type ?: "",
                page = 1,
                apiKey = API_KEY
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _mediaDetailsScreenState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { similarMediaList ->
                            _mediaDetailsScreenState.update {
                                it.copy(
                                    similarMediaList = similarMediaList,
                                    smallSimilarMediaList = similarMediaList.take(10)
                                )
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun loadMediaItem(
        id: Int,
        type: String,
        category: String,
        onFinished: () -> Unit,
    ) {
        viewModelScope.launch {
            _mediaDetailsScreenState.update {
                it.copy(
                    media = mediaRepository.getItem(
                        type = type,
                        category = category,
                        id = id
                    )
                )
            }
            onFinished()
        }
    }

    private fun loadDetails(isRefresh: Boolean) {
        viewModelScope.launch {
            detailsRepository.getDetails(
                id = mediaDetailsScreenState.value.media?.id ?: 0,
                type = mediaDetailsScreenState.value.media?.media_type ?: "",
                isRefresh = isRefresh,
                apiKey = API_KEY
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _mediaDetailsScreenState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success ->
                        result.data?.let { media ->
                            _mediaDetailsScreenState.update {
                                it.copy(
                                    media = mediaDetailsScreenState.value.media?.copy(
                                        runtime = media.runtime,
                                        status = media.status,
                                        tagline = media.tagline
                                    ),
                                    readableTime = MinutesToReadTime(
                                        media.runtime ?: 0
                                    ).invoke()
                                )
                            }
                        }

                    else -> Unit
                }

            }
        }
    }

    private val _refreshing = MutableStateFlow(false)
    val refreshing : StateFlow<Boolean> = _refreshing

    fun onRefresh(type:String){
        viewModelScope.launch {
            _refreshing.value = true
            delay(1500)
            if(type != null){
                onEvent(MediaDetailsEvents.onRefresh(type = type))
            }
            _refreshing.value = false
        }
    }

}