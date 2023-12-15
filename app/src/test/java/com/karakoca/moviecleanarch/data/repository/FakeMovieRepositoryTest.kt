package com.karakoca.moviecleanarch.data.repository

import androidx.paging.PagingData
import com.karakoca.moviecleanarch.data.remote.dto.MovieDetailsDTO
import com.karakoca.moviecleanarch.domain.model.Movie
import com.karakoca.moviecleanarch.domain.model.MovieDetail
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeMovieRepositoryTest : MovieRepository {

    private val movies = mutableListOf<MovieDetail>()

    override fun getMovies(search: String): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(imdbId: String): Response<MovieDetailsDTO> {
        TODO("Not yet implemented")
    }

    override fun getAllSavedMovies(): Flow<List<MovieDetail>> {
        return flow { emit(movies) }
    }

    override fun findMovieById(id: String): MovieDetail? {
        return movies.find { it.imdbID == id }
    }

    override suspend fun addMovie(movieDetail: MovieDetail): Long {
        movies.add(movieDetail)
        return 0
    }

    override suspend fun deleteMovie(id: Long) {
        val movie = movies.find { it.id == id }
        movies.remove(movie)
    }


}