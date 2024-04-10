package com.skyline.moviescatalog.features.movies.domain.repository

import com.skyline.moviescatalog.common.ApiState
import com.skyline.moviescatalog.data.model.Movies
import java.util.concurrent.Flow

interface MovieRepository {

    suspend fun getMovies(): Flow<ApiState<Movies>>
}