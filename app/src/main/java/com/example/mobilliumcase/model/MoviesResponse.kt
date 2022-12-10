package com.example.mobilliumcase.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MoviesDataModel>,
    @SerializedName("total_pages") val pages: Int
)