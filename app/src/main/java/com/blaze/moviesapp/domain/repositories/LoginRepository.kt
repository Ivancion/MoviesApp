package com.blaze.moviesapp.domain.repositories

import com.blaze.moviesapp.domain.models.DeleteSessionResponse
import com.blaze.moviesapp.domain.models.RequestTokenResponse
import com.blaze.moviesapp.domain.models.SessionResponse

interface LoginRepository {

    suspend fun getRequestToken(): RequestTokenResponse

    suspend fun createSessionWithLogin(
        userName: String,
        password: String,
        token: String
    ): RequestTokenResponse

    suspend fun createSessionId(token: String): SessionResponse

    fun getSessionId(): String

    fun saveSessionId()

    suspend fun deleteSession(): DeleteSessionResponse
}