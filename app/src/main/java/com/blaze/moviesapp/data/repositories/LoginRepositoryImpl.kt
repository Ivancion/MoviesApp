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
import com.blaze.moviesapp.domain.repositories.LoginRepository
import com.blaze.moviesapp.other.Constants


// TODO Need to move error handling logic to repository from use cases
class LoginRepositoryImpl(
    private val api: LoginApi,
    private val iSessionId: ISessionId,
    private val systemPreferences: ISystemPreferences,
): LoginRepository {

    override suspend fun getRequestToken(): RequestTokenResponse {
        return api.getRequestToken()
    }

    override suspend fun createSessionWithLogin(userName: String, password: String, token: String): RequestTokenResponse {
        return api.createSessionWithLogin(
            userLoginInfo = SessionWithLoginRequest(
                userName,
                password,
                token
            )
        )
    }

    override suspend fun createSessionId(token: String): SessionResponse {
        return api.createSessionId(
            sessionRequest = SessionRequest(token)
        ).also {
            iSessionId.setSessionId(it)
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

    override suspend fun deleteSession(): DeleteSessionResponse {
        return api.deleteSession(
            deleteSessionRequest = DeleteSessionRequest(iSessionId.sessionIdData?.sessionId ?: "")
        ).also {
            systemPreferences.putValue(Constants.SESSION_ID, "")
        }
    }
}