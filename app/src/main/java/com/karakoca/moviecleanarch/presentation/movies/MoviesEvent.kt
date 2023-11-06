package com.karakoca.moviecleanarch.presentation.movies

sealed class MoviesEvent {
    object Search : MoviesEvent()
    object AddOrRemoveMovie : MoviesEvent()
    object GetLocalMovies : MoviesEvent()
    object FindMovie : MoviesEvent()
}