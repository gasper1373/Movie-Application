package com.example.movieapplication.search.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapplication.main.presentation.main.MainUiState
import com.example.movieapplication.search.presentation.components.SearchMediaItem
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.desingSystem.FocusedTopBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
 fun SearchScreen(
    navController: NavController,
    state: MainUiState,
    onAction: (SearchScreenUiEvent) -> Unit,
) {
    val searchViewModel = hiltViewModel<SearchScreenViewModel>()
    val searchScreenState = searchViewModel.searchScreenState.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(top = 24.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(searchScreenState.searchList.size) {
                SearchMediaItem(
                    media = searchScreenState.searchList[it],
                    onEvent = { TODO() },
                    mainUiState = state,
                )
                if (it >= searchViewModel.searchScreenState.value.searchList.size - 1 && !state.isLoading) {
                    searchViewModel.onEvent(SearchScreenUiEvent.OnPaginate(Constants.searchScreen))
                }
            }
        }
        FocusedTopBar(searchScreenState = searchScreenState
        ){
            searchViewModel.onEvent(SearchScreenUiEvent.OnSearchQueryChanged(it))
        }
    }

}
