package com.blaze.moviesapp.domain.repositories

import androidx.paging.PagingData
import com.blaze.moviesapp.domain.models.AccountDetails
import com.blaze.moviesapp.domain.models.AddToWatchlistResponse
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.models.MovieDetail
import com.blaze.moviesapp.domain.models.MovieState
import com.blaze.moviesapp.domain.models.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getTopRatedMovies(page: Int): MoviesResponse

    suspend fun getNowPlayingMovies(page: Int): MoviesResponse

    suspend fun getUpcomingMovies(page: Int): MoviesResponse

    suspend fun getPopularMovies(page: Int): MoviesResponse

    suspend fun getTrendingMovies(): MoviesResponse

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

    fun getWatchlistPagingFlow(
        sessionId: String,
        emptyResponse: (Boolean) -> Unit
    ): Flow<PagingData<Movie>>

    fun getSearchMoviesPagingFlow(
        query: String,
        emptyResponse: (Boolean) -> Unit
    ): Flow<PagingData<Movie>>
}