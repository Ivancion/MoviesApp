package com.blaze.moviesapp.data.repositories

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.blaze.moviesapp.data.local.ISessionId
import com.blaze.moviesapp.data.local.SessionId
import com.blaze.moviesapp.other.AuthResult
import com.blaze.moviesapp.other.Constants
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AuthRepositoryImplTest {

    private lateinit var authRepository: AuthRepositoryImpl
    private lateinit var loginApi: LoginServiceFake
    private lateinit var sessionId: ISessionId
    private lateinit var systemPreferences: SystemPreferencesFake

    @BeforeEach
    fun setUp() {
        loginApi = LoginServiceFake()
        sessionId = SessionId()
        systemPreferences = SystemPreferencesFake()

        authRepository = AuthRepositoryImpl(
            loginService = loginApi,
            iSessionId = sessionId,
            systemPreferences = systemPreferences
        )
    }

    @Test
    fun `create session method should not save sessionId in memory when error occurs`() = runBlocking {
        loginApi.shouldThrowError = true
        loginApi.errorMessage = "Test create session error"
        val token = loginApi.tokenResponse.requestToken!!
        authRepository.createSessionId(token)

        assertThat(sessionId.sessionIdData?.sessionId).isNull()
    }

    @Test
    fun `create session method should save sessionId in memory`() = runBlocking {
        loginApi.shouldThrowError = false
        val token = loginApi.tokenResponse.requestToken!!
        val sessionResponse = authRepository.createSessionId(token) as AuthResult.Success

        assertThat(sessionResponse.data.sessionId).isEqualTo(sessionId.sessionIdData?.sessionId)
    }

    @Test
    fun `saveSessionId should save it in system preferences`() = runBlocking {
        loginApi.shouldThrowError = false
        sessionId.setSessionId(
            sessionId = loginApi.sessionResponse
        )
        authRepository.saveSessionId()
        assertThat(sessionId.sessionIdData?.sessionId).isEqualTo(systemPreferences.getValue(Constants.SESSION_ID))
    }

    @Test
    fun `deleteSession should delete sessionId from systemPreferences`() = runBlocking {
        loginApi.shouldThrowError = false
        sessionId.setSessionId(
            sessionId = loginApi.sessionResponse
        )
        authRepository.deleteSession()
        assertThat(systemPreferences.getValue(Constants.SESSION_ID)).isEmpty()
    }
}