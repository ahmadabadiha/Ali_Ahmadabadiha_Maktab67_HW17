package com.example.hw17.datalayer

import com.example.hw17.datalayer.database.MovieDao
import com.example.hw17.localmodel.Movie

class RoomDataSource(private val movieDao: MovieDao) : LocalDataSource {
    override suspend fun getAllMovies(): List<Movie> {
        return movieDao.getAllMovies()
    }

    override suspend fun insertMovies(vararg movies: Movie) {
        movieDao.insertMovies(*movies)
    }
}