package com.blaze.moviesapp.di

import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideGetSessionIdUseCase(
        repository: Repository
    ) = GetSessionIdUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideLoginUserUseCase(
        repository: Repository
    ) = LoginUserUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetTrendingMoviesUseCase(
        repository: Repository
    ) = GetTrendingMoviesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetMoviesByCategoryUseCase(
        repository: Repository
    ) = GetMoviesByCategoryUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetPagerHomeScreenUseCase(
        getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase
    ) = GetPagerHomeScreenUseCase(getMoviesByCategoryUseCase)

    @Provides
    @ViewModelScoped
    fun provideSearchMoviesUseCase(
        repository: Repository
    ) = SearchMoviesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetGenresUseCase(
        repository: Repository
    ) = GetGenresUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetPagerSearchScreenUseCase(
        searchMoviesUseCase: SearchMoviesUseCase,
        getGenresUseCase: GetGenresUseCase
    ) = GetPagerSearchScreenUseCase(searchMoviesUseCase, getGenresUseCase)

    @Provides
    @ViewModelScoped
    fun provideGetMovieDetailUseCase(
        repository: Repository
    ) = GetMovieDetailUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideLoadApiConfigurationUseCase(
        repository: Repository
    ) = LoadApiConfigurationUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetApiConfigurationUseCase(
        repository: Repository
    ) = GetApiConfigurationUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetMovieStatesUseCase(
        repository: Repository
    ) = GetMovieStatesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideAddToWatchlistUseCase(
        repository: Repository
    ) = AddToWatchlistUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetMovieWatchlistUseCase(
        repository: Repository
    ) = GetMovieWatchlistUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetPagerWatchlistUseCase(
        getMovieWatchlistUseCase: GetMovieWatchlistUseCase,
        getGenresUseCase: GetGenresUseCase
    ) = GetPagerWatchlistUseCase(getMovieWatchlistUseCase, getGenresUseCase)

    @Provides
    @ViewModelScoped
    fun provideDeleteSessionUseCase(
        repository: Repository
    ) = DeleteSessionUseCase(repository)
}