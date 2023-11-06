package com.karakoca.moviecleanarch.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karakoca.moviecleanarch.domain.model.MovieDetail

@Database(entities = [MovieDetail::class], version = 1, exportSchema = true)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun dao(): MovieDao
}