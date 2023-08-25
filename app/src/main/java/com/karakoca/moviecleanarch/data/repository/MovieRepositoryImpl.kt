package com.karakoca.moviecleanarch.data.repository

import com.karakoca.moviecleanarch.data.remote.MovieApi
import com.karakoca.moviecleanarch.data.remote.dto.MovieDetailsDTO
import com.karakoca.moviecleanarch.data.remote.dto.MoviesDTO
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {
    override suspend fun getMovies(search: String): Response<MoviesDTO> {
        return api.getMovies(search = search, page = 1)
    }

    override suspend fun getMovieDetails(imdbId: String): Response<MovieDetailsDTO> {
        return api.getMovieDetails(imdbId = imdbId)
    }
}