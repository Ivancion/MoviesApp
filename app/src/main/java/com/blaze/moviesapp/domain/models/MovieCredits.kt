package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("cast")
    val cast: List<Cast>? = null,
    @SerializedName("crew")
    val crew: List<Crew>? = null
)
