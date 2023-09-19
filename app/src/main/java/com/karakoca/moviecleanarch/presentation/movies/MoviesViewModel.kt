package com.karakoca.moviecleanarch.presentation.movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.karakoca.moviecleanarch.data.remote.MovieApi
import com.karakoca.moviecleanarch.domain.model.Movie
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import com.karakoca.moviecleanarch.domain.use_case.movies_usecase.MoviesUseCase
import com.karakoca.moviecleanarch.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    val repository: MovieRepository,
    val api: MovieApi
) : ViewModel() {

    private val _state = mutableStateOf<MovieState>(MovieState())
    val state: State<MovieState>
        get() = _state

    var query = mutableStateOf(Constant.DEFAULT_SEARCH)


    fun getMovies(): Flow<PagingData<Movie>> {
        return moviesUseCase.invoke(query.value).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun onEvent(event: MoviesEvent) {
        viewModelScope.launch {
            when (event) {
                is MoviesEvent.Search -> {
                    getMovies()
                }
            }
        }

    }
}