package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName


data class RequestTokenResponse(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("expires_at")
    val expiresAt: String? = null,
    @SerializedName("request_token")
    val requestToken: String? = null
)