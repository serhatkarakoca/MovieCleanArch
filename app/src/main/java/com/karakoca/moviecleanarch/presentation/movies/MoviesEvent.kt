package com.karakoca.moviecleanarch.presentation.movies

sealed class MoviesEvent {
    object Search : MoviesEvent()
}