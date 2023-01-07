package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("logo_path")
    val logoPath: String? = null,
    @SerializedName("origin_country")
    val originCountry: String? = null
)
