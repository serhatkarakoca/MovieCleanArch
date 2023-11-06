package com.karakoca.moviecleanarch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.karakoca.moviecleanarch.utils.Constant.TABLE_NAME

@Entity(TABLE_NAME)
data class MovieDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val director: String?,
    val genre: String?,
    val imdbRating: String?,
    val imdbVotes: String?,
    val plot: String?,
    val poster: String?,
    val released: String?,
    val title: String?,
    val type: String?,
    val runtime: String?,
    val year: String?,
    val imdbID: String?,
)