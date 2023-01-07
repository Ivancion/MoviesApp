package com.blaze.moviesapp.data.repositories

import com.blaze.moviesapp.data.local.*
import com.blaze.moviesapp.data.remote.MovieDBApi
import com.blaze.moviesapp.domain.models.*
import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.other.Constants.MOVIE
import com.blaze.moviesapp.other.Constants.PASSWORD
import com.blaze.moviesapp.other.Constants.SESSION_ID
import com.blaze.moviesapp.other.Constants.USERNAME
import retrofit2.Response

class RepositoryImpl(
    private val api: MovieDBApi,
    private val iRequestToken: IRequestToken,
    private val iSessionId: ISessionId,
    private val iSystemPreferences: ISystemPreferences,
    private val iApiConfiguration: IApiConfiguration,
): Repository {

    override suspend fun getRequestToken(): RequestTokenResponse {
        return api.getRequestToken().also {
            iRequestToken.setRequestToken(it)
        }
    }

    override suspend fun createSessionWithLogin(userName: String, password: String): RequestTokenResponse {
        return api.createSessionWithLogin(
            userLoginInfo = SessionWithLoginRequest(
                userName,
                password,
                "${iRequestToken.requestTokenData?.requestToken}"
            )
        )
    }

    override suspend fun createSessionId(): SessionResponse {
        return api.createSessionId(
            sessionRequest = SessionRequest(
                "${iRequestToken.requestTokenData?.requestToken}"
            )
        ).also {
            iSessionId.setSessionId(it)
        }
    }

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

    override fun getSessionId(): String {
        return iSessionId.sessionIdData?.sessionId ?: iSystemPreferences.getValue(SESSION_ID).also {
            iSessionId.setSessionId(SessionResponse(true, it))
        }
    }

    override fun saveSessionId() {
        iSystemPreferences.putValue(SESSION_ID, iSessionId.sessionIdData?.sessionId ?: "")
    }

    override suspend fun loadApiConfiguration(): ApiConfigResponse {
        return api.loadApiConfiguration().also {
            iApiConfiguration.setConfigurationData(it)
        }
    }

    override fun getApiConfiguration(): ApiConfigResponse {
        return iApiConfiguration.configurationData
    }

    override suspend fun getMovieStates(movieId: Int) : MovieState {
        return api.getMovieStates(
            movieId = movieId,
            sessionId = getSessionId()
        )
    }

    override suspend fun addToWatchlist(accountId: Int, add: Boolean, movieId: Int) : AddToWatchlistResponse {
        return api.addToWatchlist(
            accountId = accountId,
            sessionId = getSessionId(),
            addToWatchlistRequest = AddToWatchlistRequest(
                mediaType = MOVIE,
                mediaId = movieId,
                watchlist = add
            )
        )
    }

    override suspend fun getAccountDetails(): AccountDetails {
        return api.getAccountDetails(
            sessionId = getSessionId()
        )
    }

    override suspend fun getMovieWatchlist(accountId: Int, page: Int): MoviesResponse {
        return api.getMovieWatchlist(
            accountId = accountId,
            sessionId = getSessionId(),
            page = page
        )
    }

    override suspend fun deleteSession(): DeleteSessionResponse {
        return api.deleteSession(
            deleteSessionRequest = DeleteSessionRequest(iSessionId.sessionIdData?.sessionId ?: "")
        ).also {
            iSystemPreferences.putValue(SESSION_ID, "")
        }
    }

}