package com.blaze.moviesapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blaze.moviesapp.data.local.IApiConfiguration
import com.blaze.moviesapp.data.pagination.CategoryMoviesPagingSource
import com.blaze.moviesapp.data.pagination.MovieWatchlistPagingSource
import com.blaze.moviesapp.data.pagination.SearchMoviesPagingSource
import com.blaze.moviesapp.data.remote.MovieService
import com.blaze.moviesapp.domain.models.AddToWatchlistRequest
import com.blaze.moviesapp.domain.models.AddToWatchlistResponse
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.models.MovieDetail
import com.blaze.moviesapp.domain.models.MovieState
import com.blaze.moviesapp.domain.models.MoviesResponse
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Constants
import com.blaze.moviesapp.other.Constants.MOVIE
import com.blaze.moviesapp.other.MovieCategory
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(
    private val movieService: MovieService,
    private val iApiConfiguration: IApiConfiguration
) : MoviesRepository {

    override fun getTrendingMovies(): Flow<Resource<MoviesResponse>> {
        return flow {
            emit(Resource.LoadingState)
            try {
                val response = movieService.getTrendingMovies()
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR))
            }
        }
    }

    override fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>> {
        return flow {
            emit(Resource.LoadingState)
            try {
                val response = movieService.getMovieDetail(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR))
            }
        }
    }

    override fun loadApiConfiguration(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.LoadingState)
            try {
                movieService.loadApiConfiguration().also {
                    iApiConfiguration.setConfigurationData(it)
                }
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR))
            }
        }
    }

    override fun getApiConfiguration(): ApiConfigResponse {
        return iApiConfiguration.configurationData
    }

    override fun getMovieStates(movieId: Int, sessionId: String): Flow<Resource<MovieState>> {
        return flow {
            emit(Resource.LoadingState)
            try {
                val response = movieService.getMovieStates(
                    movieId = movieId,
                    sessionId = sessionId
                )
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR))
            }
        }
    }

    override fun addToWatchlist(
        add: Boolean,
        movieId: Int,
        sessionId: String
    ): Flow<Resource<AddToWatchlistResponse>> {
        return flow {
            emit(Resource.LoadingState)
            try {
                val accountDetails = movieService.getAccountDetails(sessionId = sessionId)
                val response = movieService.addToWatchlist(
                    accountId = accountDetails.id ?: 0,
                    sessionId = sessionId,
                    addToWatchlistRequest = AddToWatchlistRequest(
                        mediaType = MOVIE,
                        mediaId = movieId,
                        watchlist = add
                    )
                )
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR))
            }
        }
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
                    movieService = movieService,
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
                    movieService = movieService,
                    query = query,
                    emptyResponse = emptyResponse
                )
            }
        ).flow
    }

    override fun getCategoryMoviesPagingFlow(
        category: MovieCategory
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                CategoryMoviesPagingSource(
                    category = category,
                    movieService = movieService
                )
            }
        ).flow
    }
}