package com.karakoca.moviecleanarch.presentation.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.karakoca.moviecleanarch.Screen
import com.karakoca.moviecleanarch.presentation.movie_details.MovieDetailsViewModel
import com.karakoca.moviecleanarch.presentation.movies.MoviesEvent

@Composable
fun SavedScreen(navigation: (String) -> Unit) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit, block = { viewModel.handleEvent(MoviesEvent.GetLocalMovies) })

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(state.value.dataList?.size ?: 0) {
                state.value.dataList?.get(it)?.let { it1 ->
                    SavedMovieRow(item = it1, clickListener = { imdbId ->
                        val route = Screen.MovieDetailsScreen.route + "/$imdbId"
                        navigation.invoke(route)
                    })
                }
            }
        })
    }
}