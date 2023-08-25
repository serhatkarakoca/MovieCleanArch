package com.karakoca.moviecleanarch.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.karakoca.moviecleanarch.domain.model.Movie

data class MoviesDTO(
    @SerializedName("Response")
    val response: String?,
    @SerializedName("Search")
    val search: List<Search?>?,
    @SerializedName("totalResults")
    val totalResults: String?
) {
    fun MoviesDTO.toMovie(): List<Movie> {
        return search?.mapNotNull {
            Movie(
                it?.imdbID ?: "",
                it?.poster ?: "",
                it?.title ?: "",
                it?.type ?: "",
                it?.year ?: ""
            )
        } ?: listOf()
    }
}