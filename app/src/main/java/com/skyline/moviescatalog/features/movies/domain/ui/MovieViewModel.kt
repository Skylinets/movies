package com.skyline.moviescatalog.features.movies.domain.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyline.moviescatalog.common.doOnFailure
import com.skyline.moviescatalog.common.doOnLoading
import com.skyline.moviescatalog.common.doOnSuccess
import com.skyline.moviescatalog.data.model.Movies
import com.skyline.moviescatalog.features.movies.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private val _movieResponse: MutableState<MovieState> = mutableStateOf(MovieState())
    val response: State<MovieState> = _movieResponse


    init {
        getMovies()
    }

    private fun getMovies() = viewModelScope.launch {
        movieUseCase.getMovies()
            .doOnSuccess {
                _movieResponse.value = MovieState(
                    it.orEmpty(),
                )
            }
            .doOnFailure {
                _movieResponse.value = MovieState(
                    error = it.toString(),
                )
            }
            .doOnLoading {
                _movieResponse.value = MovieState(
                    isLoading =  true
                )
            }.collect()
    }
}


data class MovieState(
    val data: List<Movies.Results> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)