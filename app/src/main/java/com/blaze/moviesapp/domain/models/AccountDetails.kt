package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class AccountDetails(
    @SerializedName("avatar")
    val avatar: Avatar? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("iso_639_1")
    val iso6391: String? = null,
    @SerializedName("iso_3166_1")
    val iso31661: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("include_adult")
    val includeAdult: Boolean? = null,
    @SerializedName("username")
    val username: String? = null
)