package com.example.movieapplication.di

import com.example.movieapplication.main.data.repository.GenreRepositoryImpl
import com.example.movieapplication.main.data.repository.MediaRepositoryImpl
import com.example.movieapplication.main.domain.repository.GenreRepository
import com.example.movieapplication.main.domain.repository.MediaRepository
import com.example.movieapplication.media_details.data.repository.DetailsRepository
import com.example.movieapplication.media_details.data.repository.ExtraDetailsRepository
import com.example.movieapplication.media_details.domain.repository.DetailsRepositoryImpl
import com.example.movieapplication.media_details.domain.repository.ExtraDetailsRepositoryImpl
import com.example.movieapplication.search.data.repository.SearchRepositoryImpl
import com.example.movieapplication.search.domain.repository.SearchRepository
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
    ): MediaRepository

    @Binds
    @Singleton
    abstract fun bindsGenresRepository(
        genreRepositoryImpl: GenreRepositoryImpl,
    ): GenreRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepository: SearchRepositoryImpl,
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindExtraDetails(
        extraDetailsRepository: ExtraDetailsRepositoryImpl,
    ): ExtraDetailsRepository

    @Binds
    @Singleton
    abstract fun bindDetails(
        detailsRepository: DetailsRepositoryImpl,
    ): DetailsRepository

}