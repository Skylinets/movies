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
    private val useCase: MovieUseCase
) : ViewModel() {
    private val _response: MutableState<MovieState> = mutableStateOf(MovieState())
    val response: State<MovieState> = _response

    init {
        viewModelScope.launch {
            useCase.getMovies()
                .doOnSuccess {
                    _response.value = MovieState(
                        data = it.orEmpty()
                    )
                }.doOnFailure {
                    _response.value = MovieState(
                        error = it?.message.orEmpty()
                    )
                }.doOnLoading {
                    _response.value = MovieState(
                        isLoading = true
                    )
                }.collect()
        }
    }
}


data class MovieState(
    val data: List<Movies.Results> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)