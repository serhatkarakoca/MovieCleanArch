package com.karakoca.moviecleanarch.presentation.movies

import androidx.paging.PagingData
import com.karakoca.moviecleanarch.domain.model.Movie

data class MovieState(
    val search: String = "movie",
    val isLoading: Boolean = false,
    val error: String = "",
    val data: PagingData<Movie>? = null
)
