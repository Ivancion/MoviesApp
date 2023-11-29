package com.blaze.moviesapp.domain.repositories

import androidx.paging.PagingData
import com.blaze.moviesapp.domain.models.AddToWatchlistResponse
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.models.MovieDetail
import com.blaze.moviesapp.domain.models.MovieState
import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.other.MovieCategory
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getTrendingMovies(): Flow<Resource<MoviesResponse>>

    fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>>

    fun loadApiConfiguration(): Flow<Resource<Unit>>

    fun getApiConfiguration(): ApiConfigResponse

    fun getMovieStates(movieId: Int, sessionId: String): Flow<Resource<MovieState>>

    fun addToWatchlist(
        add: Boolean,
        movieId: Int,
        sessionId: String
    ): Flow<Resource<AddToWatchlistResponse>>

    fun getWatchlistPagingFlow(
        sessionId: String,
        emptyResponse: (Boolean) -> Unit
    ): Flow<PagingData<Movie>>

    fun getSearchMoviesPagingFlow(
        query: String,
        emptyResponse: (Boolean) -> Unit
    ): Flow<PagingData<Movie>>

    fun getCategoryMoviesPagingFlow(
        category: MovieCategory
    ): Flow<PagingData<Movie>>
}