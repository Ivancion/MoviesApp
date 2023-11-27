package com.blaze.moviesapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blaze.moviesapp.data.local.IApiConfiguration
import com.blaze.moviesapp.data.pagination.MovieWatchlistPagingSource
import com.blaze.moviesapp.data.pagination.SearchMoviesPagingSource
import com.blaze.moviesapp.data.remote.MovieDBApi
import com.blaze.moviesapp.domain.models.AccountDetails
import com.blaze.moviesapp.domain.models.AddToWatchlistRequest
import com.blaze.moviesapp.domain.models.AddToWatchlistResponse
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.models.MovieDetail
import com.blaze.moviesapp.domain.models.MovieState
import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Constants.MOVIE
import kotlinx.coroutines.flow.Flow

// TODO Need to move error handling logic to repository from use cases
class MoviesRepositoryImpl(
    private val api: MovieDBApi,
    private val iApiConfiguration: IApiConfiguration
): MoviesRepository {

    override suspend fun getTopRatedMovies(page: Int): MoviesResponse {
        return api.getTopRatedMovies(page = page)
    }

    override suspend fun getNowPlayingMovies(page: Int): MoviesResponse  {
        return api.getNowPlayingMovies(page = page)
    }

    override suspend fun getUpcomingMovies(page: Int): MoviesResponse  {
        return api.getUpcomingMovies(page = page)
    }

    override suspend fun getPopularMovies(page: Int): MoviesResponse  {
        return api.getPopularMovies(page = page)
    }

    override suspend fun getTrendingMovies(): MoviesResponse {
        return api.getTrendingMovies()
    }

    override suspend fun getMovieDetail(id: Int): MovieDetail {
        return api.getMovieDetail(id)
    }

    override suspend fun loadApiConfiguration(): ApiConfigResponse {
        return api.loadApiConfiguration().also {
            iApiConfiguration.setConfigurationData(it)
        }
    }

    override fun getApiConfiguration(): ApiConfigResponse {
        return iApiConfiguration.configurationData
    }

    override suspend fun getMovieStates(movieId: Int, sessionId: String) : MovieState {
        return api.getMovieStates(
            movieId = movieId,
            sessionId = sessionId
        )
    }

    override suspend fun addToWatchlist(
        accountId: Int,
        add: Boolean,
        movieId: Int,
        sessionId: String
    ) : AddToWatchlistResponse {

        return api.addToWatchlist(
            accountId = accountId,
            sessionId = sessionId,
            addToWatchlistRequest = AddToWatchlistRequest(
                mediaType = MOVIE,
                mediaId = movieId,
                watchlist = add
            )
        )
    }

    override suspend fun getAccountDetails(sessionId: String): AccountDetails {
        return api.getAccountDetails(
            sessionId = sessionId
        )
    }

    override fun getWatchlistPagingFlow(
        sessionId: String,
        emptyResponse: (Boolean) -> Unit
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                MovieWatchlistPagingSource(
                    sessionId = sessionId,
                    movieApi = api,
                    emptyResponse = emptyResponse
                )
            }
        ).flow
    }

    override fun getSearchMoviesPagingFlow(
        query: String,
        emptyResponse: (Boolean) -> Unit
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                SearchMoviesPagingSource(
                    movieApi = api,
                    query = query,
                    emptyResponse = emptyResponse
                )
            }
        ).flow
    }
}