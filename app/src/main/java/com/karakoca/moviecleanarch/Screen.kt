package com.karakoca.moviecleanarch

sealed class Screen(val route: String, val title: Int) {
    object MovieScreen : Screen("movie_screen", R.string.app_name)
    object MovieDetailsScreen : Screen("movie_details_screen", R.string.movie_details)
    object SavedScreen : Screen("saved_screen", R.string.favorites)
}
