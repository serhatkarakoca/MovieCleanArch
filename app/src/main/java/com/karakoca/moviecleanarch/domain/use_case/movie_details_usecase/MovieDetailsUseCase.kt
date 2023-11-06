package com.karakoca.moviecleanarch.domain.use_case.movie_details_usecase

import com.karakoca.moviecleanarch.data.remote.dto.toMovieDetail
import com.karakoca.moviecleanarch.domain.model.MovieDetail
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import com.karakoca.moviecleanarch.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    fun getAllMovies(): Flow<List<MovieDetail>?> = repository.getAllSavedMovies()

    fun findMovieById(id: String) = flow {
        emit(repository.findMovieById(id))
    }.flowOn(Dispatchers.IO)


    private suspend fun addMovie(movieDetail: MovieDetail) = repository.addMovie(movieDetail)
    private suspend fun removeMovie(id: Long) = repository.deleteMovie(id)

    suspend fun addOrRemoveMovie(movie: MovieDetail) {
        findMovieById(movie.imdbID ?: "").collectLatest {
            if (it != null)
                removeMovie(it.id)
            else
                addMovie(movie)
        }

    }

}