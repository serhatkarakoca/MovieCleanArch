package com.karakoca.moviecleanarch.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karakoca.moviecleanarch.domain.model.MovieDetail
import com.karakoca.moviecleanarch.utils.Constant.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieDetail): Long

    @Query("DELETE FROM $TABLE_NAME WHERE id == :movieId")
    suspend fun removeMovie(movieId: Long)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllSavedMovies(): Flow<List<MovieDetail>>

    @Query("SELECT * FROM $TABLE_NAME WHERE imdbID == :imdbId")
    fun findById(imdbId: String): MovieDetail?

}