package com.karakoca.moviecleanarch.presentation.movie_details

import com.karakoca.moviecleanarch.domain.model.MovieDetail

data class MovieDetailsState(
    val data: MovieDetail? = null,
    val dataList: List<MovieDetail>? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val isFav: Boolean = false
)
