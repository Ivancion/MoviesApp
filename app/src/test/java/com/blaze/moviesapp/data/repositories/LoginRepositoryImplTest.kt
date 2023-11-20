package com.blaze.moviesapp.data.repositories

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.blaze.moviesapp.data.local.ISessionId
import com.blaze.moviesapp.data.local.SessionId
import com.blaze.moviesapp.other.Constants
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginRepositoryImplTest {

    private lateinit var loginRepository: LoginRepositoryImpl
    private lateinit var loginApi: LoginApiFake
    private lateinit var sessionId: ISessionId
    private lateinit var systemPreferences: SystemPreferencesFake

    @BeforeEach
    fun setUp() {
        loginApi = LoginApiFake()
        sessionId = SessionId()
        systemPreferences = SystemPreferencesFake()

        loginRepository = LoginRepositoryImpl(
            api = loginApi,
            iSessionId = sessionId,
            systemPreferences = systemPreferences
        )
    }

    @Test
    fun `create session method should also save sessionId in memory`() = runBlocking {
        val token = loginApi.tokenResponse.requestToken!!
        val sessionResponse = loginRepository.createSessionId(token)

        assertThat(sessionResponse.sessionId).isEqualTo(sessionId.sessionIdData?.sessionId)
    }

    @Test
    fun `saveSessionId should save it in system preferences`() = runBlocking {
        sessionId.setSessionId(
            sessionId = loginApi.sessionResponse
        )
        loginRepository.saveSessionId()
        assertThat(sessionId.sessionIdData?.sessionId).isEqualTo(systemPreferences.getValue(Constants.SESSION_ID))
    }

    @Test
    fun `deleteSession should delete sessionId from systemPreferences`() = runBlocking {
        sessionId.setSessionId(
            sessionId = loginApi.sessionResponse
        )
        loginRepository.deleteSession()
        assertThat(systemPreferences.getValue(Constants.SESSION_ID)).isEmpty()
    }
}