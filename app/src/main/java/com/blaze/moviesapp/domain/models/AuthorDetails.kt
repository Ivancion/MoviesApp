package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("avatar_path")
    val avatarPath: String? = null,
    @SerializedName("rating")
    val rating: Int? = null
)
