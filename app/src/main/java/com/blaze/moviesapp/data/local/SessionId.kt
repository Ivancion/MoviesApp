package com.blaze.moviesapp.data.local

import com.blaze.moviesapp.domain.models.SessionResponse

interface ISessionId {
    val sessionIdData: SessionResponse?

    fun setSessionId(sessionId: SessionResponse)
}

class SessionId : ISessionId {
    private var _sessionIdData: SessionResponse? = null
    override val sessionIdData: SessionResponse?
        get() = _sessionIdData

    override fun setSessionId(sessionId: SessionResponse) {
        _sessionIdData = sessionId
    }

}