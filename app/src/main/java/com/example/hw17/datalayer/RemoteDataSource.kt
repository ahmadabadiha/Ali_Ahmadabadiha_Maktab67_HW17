package com.example.hw17.datalayer

import com.example.hw17.localmodel.Movie
import com.example.hw17.servermodel.MovieModel
import com.example.hw17.servermodel.details.MovieDetails
import retrofit2.Call

interface RemoteDataSource {
    suspend fun getPopular(): MovieModel
    suspend fun getUpcoming(): MovieModel
    suspend fun searchMovies(query: String): MovieModel
    suspend fun getMovieDetails(movie_id: String): MovieDetails
}