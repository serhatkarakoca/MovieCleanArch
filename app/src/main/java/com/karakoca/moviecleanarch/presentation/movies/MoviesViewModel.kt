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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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

    private val _moviesState: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesState: MutableStateFlow<PagingData<Movie>> get() = _moviesState

    var query = mutableStateOf(Constant.DEFAULT_SEARCH)

    init {
        onEvent(MoviesEvent.Search)
    }

    private suspend fun getMovies() {
        moviesUseCase.invoke(query.value)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
            .collect {
                _moviesState.value = it
            }
    }

    fun onEvent(event: MoviesEvent) {
        viewModelScope.launch {
            when (event) {
                is MoviesEvent.Search -> {
                    getMovies()
                }

                else -> {}
            }
        }

    }
}