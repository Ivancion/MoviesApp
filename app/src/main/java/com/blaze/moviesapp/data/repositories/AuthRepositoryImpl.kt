package com.blaze.moviesapp.data.repositories

import com.blaze.moviesapp.data.local.ISessionId
import com.blaze.moviesapp.data.local.ISystemPreferences
import com.blaze.moviesapp.data.remote.LoginApi
import com.blaze.moviesapp.domain.models.DeleteSessionRequest
import com.blaze.moviesapp.domain.models.DeleteSessionResponse
import com.blaze.moviesapp.domain.models.RequestTokenResponse
import com.blaze.moviesapp.domain.models.SessionRequest
import com.blaze.moviesapp.domain.models.SessionResponse
import com.blaze.moviesapp.domain.models.SessionWithLoginRequest
import com.blaze.moviesapp.domain.repositories.AuthRepository
import com.blaze.moviesapp.other.AuthResult
import com.blaze.moviesapp.other.Constants


class AuthRepositoryImpl(
    private val api: LoginApi,
    private val iSessionId: ISessionId,
    private val systemPreferences: ISystemPreferences,
): AuthRepository {

    override suspend fun getRequestToken(): AuthResult<RequestTokenResponse> {
        return try {
            val response = api.getRequestToken()
            AuthResult.Success(response)
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR)
        }
    }

    override suspend fun createSessionWithLogin(
        userName: String,
        password: String, token: String
    ): AuthResult<RequestTokenResponse> {
        return try {
            val response = api.createSessionWithLogin(
                userLoginInfo = SessionWithLoginRequest(
                    userName,
                    password,
                    token
                )
            )
            AuthResult.Success(response)
        } catch (e: Exception) {
            if(e.message?.contains("401") == true) {
                AuthResult.Error(Constants.INVALID_LOGIN_DATA)
            } else {
                AuthResult.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR)
            }
        }
    }

    override suspend fun createSessionId(token: String): AuthResult<SessionResponse> {
        return try {
            val response = api.createSessionId(
                sessionRequest = SessionRequest(token)
            ).also {
                cacheInMemorySessionId(it)
            }
            AuthResult.Success(response)
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR)
        }
    }

    override fun getSessionId(): String {
        return iSessionId.sessionIdData?.sessionId ?: systemPreferences.getValue(Constants.SESSION_ID).also {
            iSessionId.setSessionId(SessionResponse(true, it))
        }
    }

    override fun saveSessionId() {
        systemPreferences.putValue(Constants.SESSION_ID, iSessionId.sessionIdData?.sessionId ?: "")
    }

    override suspend fun deleteSession(): AuthResult<DeleteSessionResponse> {
        return try {
            val response = api.deleteSession(
                deleteSessionRequest = DeleteSessionRequest(iSessionId.sessionIdData?.sessionId ?: "")
            ).also {
                clearSessionCache()
            }
            AuthResult.Success(response)
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: e.message ?: Constants.UNKNOWN_ERROR)
        }
    }

    private fun clearSessionCache() {
        systemPreferences.putValue(Constants.SESSION_ID, "")
    }

    private fun cacheInMemorySessionId(session: SessionResponse) {
        iSessionId.setSessionId(session)
    }
}