package com.blaze.moviesapp.data.repositories

import com.blaze.moviesapp.data.remote.LoginService
import com.blaze.moviesapp.domain.models.DeleteSessionRequest
import com.blaze.moviesapp.domain.models.DeleteSessionResponse
import com.blaze.moviesapp.domain.models.RequestTokenResponse
import com.blaze.moviesapp.domain.models.SessionRequest
import com.blaze.moviesapp.domain.models.SessionResponse
import com.blaze.moviesapp.domain.models.SessionWithLoginRequest

class LoginServiceFake: LoginService {

    var tokenResponse = RequestTokenResponse(
        success = true,
        expiresAt = null,
        requestToken = "test_token"
    )

    var sessionResponse = SessionResponse(
        success = true,
        sessionId = "test_session_id"
    )

    var shouldThrowError = false
    var errorMessage = "Test error"

    override suspend fun getRequestToken(apiKey: String): RequestTokenResponse {
        if(shouldThrowError) {
            throw Exception(errorMessage)
        }
        return tokenResponse
    }

    override suspend fun createSessionWithLogin(
        apiKey: String,
        userLoginInfo: SessionWithLoginRequest
    ): RequestTokenResponse {
        if(shouldThrowError) {
            throw Exception(errorMessage)
        }
        return tokenResponse
    }

    override suspend fun createSessionId(
        apiKey: String,
        sessionRequest: SessionRequest
    ): SessionResponse {
        if(shouldThrowError) {
            throw Exception(errorMessage)
        }
        return sessionResponse
    }

    override suspend fun deleteSession(
        apiKey: String,
        deleteSessionRequest: DeleteSessionRequest
    ): DeleteSessionResponse {
        if(shouldThrowError) {
            throw Exception(errorMessage)
        }
        sessionResponse = sessionResponse.copy(
            sessionId = null
        )
        return DeleteSessionResponse(success = true)
    }
}