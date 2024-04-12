package com.skyline.moviescatalog.features.movies.domain.usecase

import com.skyline.moviescatalog.common.ApiState
import com.skyline.moviescatalog.common.map
import com.skyline.moviescatalog.data.model.Movies
import com.skyline.moviescatalog.features.movies.domain.mapper.MovieMapper
import com.skyline.moviescatalog.features.movies.domain.repository.MovieRepository
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repo: MovieRepository,
    private val mapper: MovieMapper
) {
    @Provides
    suspend fun getMovies(): Flow<ApiState<List<Movies.Results>?>> {
        return repo.getMovies().map { results ->
            results.map {
              mapper.fromMap(it)
            }
        }
    }
}