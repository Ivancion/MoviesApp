package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class SessionWithLoginRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("request_token")
    val requestToken: String
)