package com.karakoca.moviecleanarch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.karakoca.moviecleanarch.data.datasource.local.MovieDao
import com.karakoca.moviecleanarch.data.remote.MovieApi
import com.karakoca.moviecleanarch.data.remote.dto.MovieDetailsDTO
import com.karakoca.moviecleanarch.domain.model.Movie
import com.karakoca.moviecleanarch.domain.model.MovieDetail
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import com.karakoca.moviecleanarch.presentation.movies.MoviesPagingSource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val localDb: MovieDao
) : MovieRepository {

    override fun getMovies(search: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(api, search)
            },
            initialKey = 1
        ).flow
    }

    override suspend fun getMovieDetails(imdbId: String): Response<MovieDetailsDTO> {
        return api.getMovieDetails(imdbId = imdbId)
    }

    override fun getAllSavedMovies(): Flow<List<MovieDetail>> {
        return localDb.getAllSavedMovies().flowOn(Dispatchers.IO)
    }

    override fun findMovieById(id: String): MovieDetail? {
        return localDb.findById(id)
    }

    override suspend fun addMovie(movieDetail: MovieDetail): Long {
        return localDb.addMovie(movie = movieDetail)
    }

    override suspend fun deleteMovie(id: Long) {
        localDb.removeMovie(id)
    }
}