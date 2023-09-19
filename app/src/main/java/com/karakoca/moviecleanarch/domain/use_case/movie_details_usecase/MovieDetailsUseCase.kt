package com.karakoca.moviecleanarch.domain.use_case.movie_details_usecase

import com.karakoca.moviecleanarch.data.remote.dto.toMovieDetail
import com.karakoca.moviecleanarch.domain.model.MovieDetail
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import com.karakoca.moviecleanarch.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {
    fun executeMovieDetails(imdbId: String): Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())

            val movie = repository.getMovieDetails(imdbId).body()

            if (movie != null)
                emit(Resource.Success(movie.toMovieDetail()))
            else
                emit(Resource.Error(message = "Error", error = null))

        } catch (e: Exception) {
            emit(Resource.Error(error = e, message = e.message))
        }
    }
}