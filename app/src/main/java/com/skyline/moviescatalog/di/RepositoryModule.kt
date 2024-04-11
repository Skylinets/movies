package com.skyline.moviescatalog.di

import com.skyline.moviescatalog.data.repository.MovieRepositoryImpl
import com.skyline.moviescatalog.features.movies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(
        repo:MovieRepositoryImpl
    ): MovieRepository
}