package com.example.movieapplication.main.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapplication.ui.theme.MovieApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieApplicationTheme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainUiState = mainViewModel.mainUiState.collectAsState().value

                Navigation(
                    mainUiState = mainUiState, onEvent = mainViewModel::onEvent,

                )
            }
        }
    }
}
