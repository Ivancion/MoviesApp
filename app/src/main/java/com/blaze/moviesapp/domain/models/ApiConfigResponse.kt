package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class ApiConfigResponse(
    @SerializedName("images")
    val images: ImagesConfig? = null,
    @SerializedName("change_keys")
    val changeKeys: List<String>? = null
)
