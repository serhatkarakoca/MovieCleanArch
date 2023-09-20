package com.karakoca.moviecleanarch.presentation.movie_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karakoca.moviecleanarch.domain.use_case.movie_details_usecase.MovieDetailsUseCase
import com.karakoca.moviecleanarch.utils.Constant.IMDB_ID
import com.karakoca.moviecleanarch.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MovieDetailsState())
    val state: State<MovieDetailsState>
        get() = _state

    val showCustomTab = mutableStateOf(false)

    var imdbId: String = ""

    init {
        savedStateHandle.get<String>(IMDB_ID)?.let {
            imdbId = it
            getMovieDetails(it)
        }
    }

    private fun getMovieDetails(imdbId: String) {
        movieDetailsUseCase.executeMovieDetails(imdbId).onEach {
            when (it) {
                is Resource.Loading -> {
                    _state.value = MovieDetailsState(loading = true)
                }

                is Resource.Success -> {
                    _state.value = MovieDetailsState(loading = false, data = it.data)
                }

                is Resource.Error -> {
                    _state.value = MovieDetailsState(loading = false, error = it.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}