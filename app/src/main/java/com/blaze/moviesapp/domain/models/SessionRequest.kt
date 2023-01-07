package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class SessionRequest(
    @SerializedName("request_token")
    val requestToken: String
)