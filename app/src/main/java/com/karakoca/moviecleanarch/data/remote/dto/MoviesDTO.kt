package com.karakoca.moviecleanarch.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MoviesDTO(
    @SerializedName("Response")
    val response: String?,
    @SerializedName("Search")
    val search: List<Search?>?,
    @SerializedName("totalResults")
    val totalResults: String?
)