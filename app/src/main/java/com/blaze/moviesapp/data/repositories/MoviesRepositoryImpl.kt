package com.blaze.moviesapp.data.repositories

import com.blaze.moviesapp.data.local.*
import com.blaze.moviesapp.data.remote.MovieDBApi
import com.blaze.moviesapp.domain.models.*
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Constants.MOVIE

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

    override suspend fun searchMovies(query: String, page: Int): MoviesResponse {
        return api.searchMovies(
            query = query,
            page = page
        )
    }

    override suspend fun getGenres(): GenresResponse {
        return api.getGenres()
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

    override suspend fun getMovieWatchlist(accountId: Int, page: Int, sessionId: String): MoviesResponse {
        return api.getMovieWatchlist(
            accountId = accountId,
            sessionId = sessionId,
            page = page
        )
    }
}