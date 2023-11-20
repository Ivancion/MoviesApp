package com.blaze.moviesapp.domain.repositories

import com.blaze.moviesapp.domain.models.*

interface MoviesRepository {

    suspend fun getTopRatedMovies(page: Int): MoviesResponse

    suspend fun getNowPlayingMovies(page: Int): MoviesResponse

    suspend fun getUpcomingMovies(page: Int): MoviesResponse

    suspend fun getPopularMovies(page: Int): MoviesResponse

    suspend fun getTrendingMovies(): MoviesResponse

    suspend fun searchMovies(
        query: String,
        page: Int
    ) : MoviesResponse

    suspend fun getGenres(): GenresResponse

    suspend fun getMovieDetail(id: Int): MovieDetail

    suspend fun loadApiConfiguration(): ApiConfigResponse

    fun getApiConfiguration(): ApiConfigResponse

    suspend fun getMovieStates(movieId: Int, sessionId: String) : MovieState

    suspend fun addToWatchlist(
        accountId: Int,
        add: Boolean,
        movieId: Int,
        sessionId: String
    ) : AddToWatchlistResponse

    suspend fun getAccountDetails(sessionId: String): AccountDetails

    suspend fun getMovieWatchlist(
        accountId: Int,
        page: Int,
        sessionId: String
    ): MoviesResponse
}