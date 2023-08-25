package com.karakoca.moviecleanarch.data.remote

import com.karakoca.moviecleanarch.data.local.Keys
import com.karakoca.moviecleanarch.data.remote.dto.MovieDetailsDTO
import com.karakoca.moviecleanarch.data.remote.dto.MoviesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET
    suspend fun getMovies(
        @Query("apikey") apiKey: String = Keys.apiKey(),
        @Query("s") search: String = "movie",
        @Query("page") page: Int = 1
    ): Response<MoviesDTO>

    @GET
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String = Keys.apiKey(),
        @Query("i") search: String
    ): Response<MovieDetailsDTO>

}