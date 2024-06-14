package com.example.movieapplication.di

import com.example.movieapplication.main.data.repository.GenreRepositoryImpl
import com.example.movieapplication.main.data.repository.MediaRepositoryImpl
import com.example.movieapplication.main.domain.repository.GenreRepository
import com.example.movieapplication.main.domain.repository.MediaListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsMediaRepository(
        mediaRepositoryImpl: MediaRepositoryImpl,
    ): MediaListRepository

    @Binds
    @Singleton
    abstract fun bindsGenresRepository(
        genreRepositoryImpl: GenreRepositoryImpl,
    ): GenreRepository


}