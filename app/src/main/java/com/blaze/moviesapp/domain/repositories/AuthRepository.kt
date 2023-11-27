package com.blaze.moviesapp.domain.repositories

import com.blaze.moviesapp.domain.models.DeleteSessionResponse
import com.blaze.moviesapp.domain.models.RequestTokenResponse
import com.blaze.moviesapp.domain.models.SessionResponse
import com.blaze.moviesapp.other.AuthResult

interface AuthRepository {

    suspend fun getRequestToken(): AuthResult<RequestTokenResponse>

    suspend fun createSessionWithLogin(
        userName: String,
        password: String,
        token: String
    ): AuthResult<RequestTokenResponse>

    suspend fun createSessionId(token: String): AuthResult<SessionResponse>

    fun getSessionId(): String

    fun saveSessionId()

    suspend fun deleteSession(): AuthResult<DeleteSessionResponse>
}