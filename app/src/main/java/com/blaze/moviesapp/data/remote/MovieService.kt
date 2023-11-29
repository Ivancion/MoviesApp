package com.blaze.moviesapp.data.remote

import com.blaze.moviesapp.BuildConfig
import com.blaze.moviesapp.domain.models.*
import com.blaze.moviesapp.other.Constants.DESC
import com.blaze.moviesapp.other.Constants.MOVIE
import com.blaze.moviesapp.other.Constants.REVIEWS_AND_CREDITS
import com.blaze.moviesapp.other.Constants.WEEK
import retrofit2.http.*

interface MovieService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ) : MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ) : MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ) : MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ) : MoviesResponse

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") mediaType: String = MOVIE,
        @Path("time_window") timeWindow: String = WEEK,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1
    ) : MoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean = true
    ) : MoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : GenresResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("append_to_response") appendToResponse: String = REVIEWS_AND_CREDITS
    ) : MovieDetail

    @GET("configuration")
    suspend fun loadApiConfiguration(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : ApiConfigResponse

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieStates(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("session_id") sessionId: String
    ) : MovieState

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("session_id") sessionId: String,
        @Body addToWatchlistRequest: AddToWatchlistRequest
    ) : AddToWatchlistResponse

    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("session_id") sessionId: String
    ) : AccountDetails

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getMovieWatchlist(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String = DESC,
        @Query("page") page: Int = 1
    ) : MoviesResponse
}