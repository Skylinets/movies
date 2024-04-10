package com.skyline.moviescatalog.data.repository

import com.skyline.moviescatalog.common.ApiState
import com.skyline.moviescatalog.common.base.BaseRepository
import com.skyline.moviescatalog.data.model.Movies
import com.skyline.moviescatalog.data.network.ApiService
import com.skyline.moviescatalog.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository, BaseRepository() {
    override suspend fun getMovies(): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getMoviesList()
    }
}