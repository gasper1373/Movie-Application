@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.movieapplication.util.desingSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapplication.R
import com.example.movieapplication.search.presentation.SearchScreenState
import com.example.movieapplication.util.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonFocusedTopBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
) {
    CenterAlignedTopAppBar(
        title = {
            NonFocusedSearchBar(
                modifier = Modifier
                    .height(50.dp)
                    .clickable {
                        navController.navigate(Route.SEARCH_SCREEN)
                    },
                placeholderText = stringResource(R.string.search_for_a_movie_or_tv_series),
            )
        }, scrollBehavior = scrollBehavior
    )
}

@Composable
fun FocusedTopBar(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    searchScreenState: SearchScreenState,
    onSearch: (String) -> Unit = {},
) {
    CenterAlignedTopAppBar(title = {
        SearchBar(
            leadingIcon = {
                Icon(
                    Icons.Rounded.Search,
                    null,
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(50.dp),
            placeholder = stringResource(R.string.search_for_a_movie_or_tv_series),
            searchScreenState = searchScreenState,
        ) {
            onSearch(it)
        }
    })
}



