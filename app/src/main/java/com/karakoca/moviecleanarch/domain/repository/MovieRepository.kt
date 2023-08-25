package com.karakoca.moviecleanarch.domain.repository

import com.karakoca.moviecleanarch.data.remote.dto.MovieDetailsDTO
import com.karakoca.moviecleanarch.data.remote.dto.MoviesDTO
import retrofit2.Response

interface MovieRepository {
    suspend fun getMovies(search: String): Response<MoviesDTO>
    suspend fun getMovieDetails(imdbId: String): Response<MovieDetailsDTO>
}