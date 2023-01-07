package com.blaze.moviesapp.data.local

import com.blaze.moviesapp.domain.models.RequestTokenResponse

interface IRequestToken {
    val requestTokenData: RequestTokenResponse?

    fun setRequestToken(requestToken: RequestTokenResponse)
}

class RequestToken : IRequestToken {
    private var _requestTokenData: RequestTokenResponse? = null
    override val requestTokenData: RequestTokenResponse?
        get() = _requestTokenData

    override fun setRequestToken(requestToken: RequestTokenResponse) {
        _requestTokenData = requestToken
    }

}