package com.example.movieapplication.main.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.util.query
import com.example.movieapplication.main.presentation.home.MediaHomeScreen
import com.example.movieapplication.main.presentation.moviesAndTvSeries.MediaListScreen
import com.example.movieapplication.search.presentation.SearchScreenUiEvent
import com.example.movieapplication.search.presentation.SearchScreenViewModel
import com.example.movieapplication.util.BottomNavRoute
import com.example.movieapplication.util.Constants
import com.example.movieapplication.util.desingSystem.FocusedTopBar
import com.example.movieapplication.util.desingSystem.MovieScaffold
import com.example.movieapplication.util.desingSystem.NonFocusedTopBar

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MediaMainScreen(
    navController: NavController,
    mainUiState: MainUiState,
    onEvent: (MainUiEvents) -> Unit,
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel(),
    viewModel: MainViewModel = hiltViewModel(),
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = "Popular",
            selectedIcon = Icons.Filled.LocalFireDepartment,
            unselectedIcon = Icons.Outlined.LocalFireDepartment,
        ),
        BottomNavigationItem(
            title = "Tv Series",
            selectedIcon = Icons.Filled.LiveTv,
            unselectedIcon = Icons.Outlined.LiveTv
        )
    )

    val selectedItem = rememberSaveable {
        mutableIntStateOf(0)
    }

    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior()

    val searchState by searchScreenViewModel.searchScreenState.collectAsState()

    val bottomBarNavController = rememberNavController()

    MovieScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topAppBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    if (searchState.isFocused) {
                        FocusedTopBar(
                            searchScreenState = searchState,
                            onSearch = { query ->
                                searchScreenViewModel.onEvent(
                                    SearchScreenUiEvent.OnSearchQueryChanged(
                                        query
                                    )
                                )
                            })
                    } else {
                        NonFocusedTopBar(navController = navController)
                    }
                }, scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem.intValue == index,
                        onClick = {
                            selectedItem.intValue = index
                            when (selectedItem.intValue) {
                                0 -> {
                                    bottomBarNavController.navigate(BottomNavRoute.MEDIA_HOME_SCREEN)
                                }

                                1 -> bottomBarNavController.navigate(
                                    "${BottomNavRoute.MEDIA_LIST_SCREEN}?type=${Constants.popularScreen}"
                                )

                                2 -> bottomBarNavController.navigate(
                                    "${BottomNavRoute.MEDIA_LIST_SCREEN}?type=${Constants.tvSeriesScreen}"
                                )
                            }
                        },
                        label = {
                            Text(
                                text = item.title,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItem.intValue) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    )
                }
            }
        },
        content = { paddingValues ->
                BottomNavigationScreens(
                    selectedItem = selectedItem,
                    navController = navController,
                    bottomBarNavController = bottomBarNavController,
                    mainUiState = mainUiState,
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {}
            }
        )
}

@Composable
fun BottomNavigationScreens(
    selectedItem: MutableState<Int>,
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomBarNavController: NavHostController,
    mainUiState: MainUiState,
    onEvent: (MainUiEvents) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = bottomBarNavController,
        startDestination = BottomNavRoute.MEDIA_HOME_SCREEN
    ) {
        composable(BottomNavRoute.MEDIA_HOME_SCREEN) {
            MediaHomeScreen(
                navController = navController,
                bottomBarNavController = bottomBarNavController,
                state = mainUiState,
                onEvent = onEvent,
                onClick = {},
                onRefresh = {},
                refreshing = false
            )
        }
        composable(
            "${BottomNavRoute.MEDIA_LIST_SCREEN}?type={type}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            MediaListScreen(
                selectedItem = selectedItem,
                navController = navController,
                bottomBarNavController = bottomBarNavController,
                navBackStackEntry = navBackStackEntry,
                state = mainUiState,
                onEvent = onEvent,
                refreshing = false,
                onRefresh = {}
            )
        }
    }
}