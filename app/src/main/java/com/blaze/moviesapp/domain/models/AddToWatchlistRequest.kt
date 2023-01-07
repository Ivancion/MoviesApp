package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class AddToWatchlistRequest(
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("media_id")
    val mediaId: Int,
    val watchlist: Boolean
)
