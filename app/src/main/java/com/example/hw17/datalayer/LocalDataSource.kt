package com.example.hw17.datalayer

import com.example.hw17.localmodel.Movie

interface LocalDataSource {
    suspend fun getAllMovies(): List<Movie>
    suspend fun insertMovies(vararg movies: Movie)
}