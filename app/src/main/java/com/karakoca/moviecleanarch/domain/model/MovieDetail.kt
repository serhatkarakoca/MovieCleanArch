package com.karakoca.moviecleanarch.domain.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("Director")
    val director: String?,
    @SerializedName("Genre")
    val genre: String?,
    @SerializedName("imdbRating")
    val imdbRating: String?,
    @SerializedName("imdbVotes")
    val imdbVotes: String?,
    @SerializedName("Plot")
    val plot: String?,
    @SerializedName("Poster")
    val poster: String?,
    @SerializedName("Released")
    val released: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("Runtime")
    val runtime: String?,
    @SerializedName("Year")
    val year: String?
)