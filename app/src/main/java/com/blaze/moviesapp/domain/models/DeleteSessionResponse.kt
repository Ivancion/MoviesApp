package com.blaze.moviesapp.domain.models

import com.google.gson.annotations.SerializedName

data class DeleteSessionResponse(
    @SerializedName("success")
    val success: Boolean? = null
)