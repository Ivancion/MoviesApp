package com.blaze.moviesapp.data.remote

import com.blaze.moviesapp.BuildConfig
import com.blaze.moviesapp.domain.models.DeleteSessionRequest
import com.blaze.moviesapp.domain.models.DeleteSessionResponse
import com.blaze.moviesapp.domain.models.RequestTokenResponse
import com.blaze.moviesapp.domain.models.SessionRequest
import com.blaze.moviesapp.domain.models.SessionResponse
import com.blaze.moviesapp.domain.models.SessionWithLoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @GET("authentication/token/new")
    suspend fun getRequestToken(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): RequestTokenResponse

    @POST("authentication/token/validate_with_login")
    suspend fun createSessionWithLogin(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Body userLoginInfo: SessionWithLoginRequest
    ): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSessionId(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Body sessionRequest: SessionRequest
    ): SessionResponse

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Body deleteSessionRequest: DeleteSessionRequest
    ) : DeleteSessionResponse
}