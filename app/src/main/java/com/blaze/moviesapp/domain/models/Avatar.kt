package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("gravatar")
    val gravatar: Gravatar? = null
)
