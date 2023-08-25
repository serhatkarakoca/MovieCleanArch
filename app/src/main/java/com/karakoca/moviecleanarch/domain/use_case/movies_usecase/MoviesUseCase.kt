package com.karakoca.moviecleanarch.domain.use_case.movies_usecase

import com.karakoca.moviecleanarch.data.remote.dto.toMovie
import com.karakoca.moviecleanarch.domain.model.Movie
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import com.karakoca.moviecleanarch.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    fun executeMovies(search: String): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())

            val movies = repository.getMovies(search).body()

            if (movies?.response?.lowercase() == "true")
                emit(Resource.Success(movies.toMovie()))
            else
                emit(Resource.Error(message = "Error", error = null))

        } catch (e: Exception) {
            emit(Resource.Error(error = e, message = null))
        }
    }
}