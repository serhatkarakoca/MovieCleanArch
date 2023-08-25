package com.karakoca.moviecleanarch.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.karakoca.moviecleanarch.domain.model.MovieDetail

data class MovieDetailsDTO(
    @SerializedName("Actors")
    val actors: String?,
    @SerializedName("Awards")
    val awards: String?,
    @SerializedName("BoxOffice")
    val boxOffice: String?,
    @SerializedName("Country")
    val country: String?,
    @SerializedName("DVD")
    val dVD: String?,
    @SerializedName("Director")
    val director: String?,
    @SerializedName("Genre")
    val genre: String?,
    @SerializedName("imdbID")
    val imdbID: String?,
    @SerializedName("imdbRating")
    val imdbRating: String?,
    @SerializedName("imdbVotes")
    val imdbVotes: String?,
    @SerializedName("Language")
    val language: String?,
    @SerializedName("Metascore")
    val metascore: String?,
    @SerializedName("Plot")
    val plot: String?,
    @SerializedName("Poster")
    val poster: String?,
    @SerializedName("Production")
    val production: String?,
    @SerializedName("Rated")
    val rated: String?,
    @SerializedName("Ratings")
    val ratings: List<Rating?>?,
    @SerializedName("Released")
    val released: String?,
    @SerializedName("Response")
    val response: String?,
    @SerializedName("Runtime")
    val runtime: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("Website")
    val website: String?,
    @SerializedName("Writer")
    val writer: String?,
    @SerializedName("Year")
    val year: String?
) {
    fun MovieDetailsDTO.toMovieDetail(): MovieDetail {
        return MovieDetail(
            director,
            genre,
            imdbRating,
            imdbVotes,
            plot,
            poster,
            released,
            title,
            type,
            runtime,
            year
        )
    }
}