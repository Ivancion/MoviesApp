package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class MovieState(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("favourite")
    val favourite: Boolean? = null,
    @SerializedName("watchlist")
    val watchlist: Boolean? = null
)
