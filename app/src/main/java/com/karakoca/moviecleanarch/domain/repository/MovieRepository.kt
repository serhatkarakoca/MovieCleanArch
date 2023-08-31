package com.karakoca.moviecleanarch.domain.repository

import androidx.paging.PagingData
import com.karakoca.moviecleanarch.data.remote.dto.MovieDetailsDTO
import com.karakoca.moviecleanarch.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository {
    fun getMovies(search: String): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(imdbId: String): Response<MovieDetailsDTO>
}