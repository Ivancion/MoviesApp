package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class AddToWatchlistResponse(
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null
)
