package com.karakoca.moviecleanarch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.karakoca.moviecleanarch.data.remote.MovieApi
import com.karakoca.moviecleanarch.data.remote.dto.MovieDetailsDTO
import com.karakoca.moviecleanarch.domain.model.Movie
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import com.karakoca.moviecleanarch.presentation.movies.MoviesPagingSource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {

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
}