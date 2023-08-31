package com.karakoca.moviecleanarch.domain.use_case.movies_usecase

import androidx.paging.PagingData
import com.karakoca.moviecleanarch.domain.model.Movie
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(search: String): Flow<PagingData<Movie>> = repository.getMovies(search)
}