package com.example.hw17.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hw17.localmodel.Movie

@Dao
interface MovieDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movies: Movie)

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<Movie>
}