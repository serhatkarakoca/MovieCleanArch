package com.karakoca.moviecleanarch.presentation.movie_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karakoca.moviecleanarch.domain.use_case.movie_details_usecase.MovieDetailsUseCase
import com.karakoca.moviecleanarch.presentation.movies.MoviesEvent
import com.karakoca.moviecleanarch.utils.Constant.IMDB_ID
import com.karakoca.moviecleanarch.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState>
        get() = _state

    val showCustomTab = mutableStateOf(false)

    var imdbId: String = ""

    init {
        savedStateHandle.get<String>(IMDB_ID)?.let {
            imdbId = it
            getMovieDetails(it)
        }
    }

    fun handleEvent(event: MoviesEvent) {
        viewModelScope.launch {
            when (event) {
                MoviesEvent.AddOrRemoveMovie -> addOrRemove()
                MoviesEvent.GetLocalMovies -> getLocalMovies()
                MoviesEvent.FindMovie -> findMovie()
                else -> Unit
            }
        }
    }

    private fun getMovieDetails(imdbId: String) {
        movieDetailsUseCase.executeMovieDetails(imdbId).onEach {
            when (it) {
                is Resource.Loading -> {
                    _state.emit(_state.value.copy(loading = true))
                }

                is Resource.Success -> {
                    _state.emit(_state.value.copy(loading = false, data = it.data, error = null))
                    handleEvent(MoviesEvent.FindMovie)
                }

                is Resource.Error -> {
                    _state.emit(_state.value.copy(loading = false, error = it.message, data = null))
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getLocalMovies() {
        movieDetailsUseCase.getAllMovies().collectLatest {
            _state.emit(_state.value.copy(dataList = it))
        }
    }

    private suspend fun findMovie() {
        movieDetailsUseCase.findMovieById(imdbId).collectLatest {
            _state.emit(_state.value.copy(isFav = it != null))
        }
    }

    private suspend fun addOrRemove() {
        val movie = _state.value.data
        if (movie != null) {
            movieDetailsUseCase.addOrRemoveMovie(movie)
            findMovie()
        }
    }
}