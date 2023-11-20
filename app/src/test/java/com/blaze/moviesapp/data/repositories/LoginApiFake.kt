package com.blaze.moviesapp.data.repositories

import com.blaze.moviesapp.data.remote.LoginApi
import com.blaze.moviesapp.domain.models.DeleteSessionRequest
import com.blaze.moviesapp.domain.models.DeleteSessionResponse
import com.blaze.moviesapp.domain.models.RequestTokenResponse
import com.blaze.moviesapp.domain.models.SessionRequest
import com.blaze.moviesapp.domain.models.SessionResponse
import com.blaze.moviesapp.domain.models.SessionWithLoginRequest

class LoginApiFake: LoginApi {

    var tokenResponse = RequestTokenResponse(
        success = true,
        expiresAt = null,
        requestToken = "test_token"
    )

    var sessionResponse = SessionResponse(
        success = true,
        sessionId = "test_session_id"
    )

    override suspend fun getRequestToken(apiKey: String): RequestTokenResponse {
        return tokenResponse
    }

    override suspend fun createSessionWithLogin(
        apiKey: String,
        userLoginInfo: SessionWithLoginRequest
    ): RequestTokenResponse {
        return tokenResponse
    }

    override suspend fun createSessionId(
        apiKey: String,
        sessionRequest: SessionRequest
    ): SessionResponse {
        return sessionResponse
    }

    override suspend fun deleteSession(
        apiKey: String,
        deleteSessionRequest: DeleteSessionRequest
    ): DeleteSessionResponse {
        sessionResponse = sessionResponse.copy(
            sessionId = null
        )
        return DeleteSessionResponse(success = true)
    }
}