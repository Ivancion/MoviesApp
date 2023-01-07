package com.blaze.moviesapp.domain.repositories

import com.blaze.moviesapp.domain.models.*
import retrofit2.Response

interface Repository {

    suspend fun getRequestToken(): RequestTokenResponse

    suspend fun createSessionWithLogin(
        userName: String,
        password: String
    ): RequestTokenResponse

    suspend fun createSessionId(): SessionResponse

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

    fun getSessionId(): String

    fun saveSessionId()

    suspend fun loadApiConfiguration(): ApiConfigResponse

    fun getApiConfiguration(): ApiConfigResponse

    suspend fun getMovieStates(movieId: Int) : MovieState

    suspend fun addToWatchlist(accountId: Int, add: Boolean, movieId: Int) : AddToWatchlistResponse

    suspend fun getAccountDetails(): AccountDetails

    suspend fun getMovieWatchlist(
        accountId: Int,
        page: Int
    ): MoviesResponse

    suspend fun deleteSession(): DeleteSessionResponse
}