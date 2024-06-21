package com.example.movieapplication.search.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapplication.main.presentation.main.MainUiState
import com.example.movieapplication.search.presentation.components.SearchMediaItem
import com.example.movieapplication.ui.theme.MovieApplicationTheme
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.desingSystem.FocusedTopBar
import kotlin.math.roundToInt

//@Composable
//fun SearchScreenRot(
//    //navController: NavController,
//    viewModel: SearchScreenViewModel = hiltViewModel(),
//) {
//    SearchScreen(
//        state = viewModel.searchScreenState,
//        onAction = viewModel::onEvent,
//
//        )
//}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
 fun SearchScreen(
    navController: NavController,
    state: MainUiState,
    onAction: (SearchScreenUiEvent) -> Unit,
) {
    val searchViewModel = hiltViewModel<SearchScreenViewModel>()
    val searchScreenState = searchViewModel.searchScreenState.value
    val toolBarHeight = with(LocalDensity.current) { 24.dp.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember {
        mutableFloatStateOf(0f)
    }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolBarHeight, 0f)
                return Offset.Zero
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(top = 24.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(searchScreenState.searchList.size) {
                SearchMediaItem(
                    media = searchScreenState.searchList[it],
                    onEvent = {},
                    mainUiState = state,
                )
                if (it >= searchViewModel.searchScreenState.value.searchList.size - 1 && !state.isLoading) {
                    searchViewModel.onEvent(SearchScreenUiEvent.OnPaginate(Constants.searchScreen))
                }
            }
        }
        FocusedTopBar(
            toolbarOffsetHeightPx =toolbarOffsetHeightPx.floatValue.roundToInt(),
            searchScreenState = searchScreenState
        ){
            searchViewModel.onEvent(SearchScreenUiEvent.OnSearchQueryChanged(it))
        }
    }

}
