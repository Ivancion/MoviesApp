package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class DeleteSessionRequest(
    @SerializedName("session_id")
    val sessionId: String
)
