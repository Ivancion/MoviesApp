package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("session_id")
    val sessionId: String? = null
)
