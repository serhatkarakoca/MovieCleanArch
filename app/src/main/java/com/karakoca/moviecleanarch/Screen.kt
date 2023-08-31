package com.karakoca.moviecleanarch

sealed class Screen(val route: String) {
    object MovieScreen : Screen("movie_screen")
    object MovieDetailsScreen : Screen("movie_details_screen")
}
