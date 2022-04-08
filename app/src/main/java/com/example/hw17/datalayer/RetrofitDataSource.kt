package com.example.hw17.datalayer

import com.example.hw17.servermodel.MovieModel
import com.example.hw17.servermodel.details.MovieDetails
import retrofit2.Call


class RetrofitDataSource(private val retrofitService: RetrofitService) : RemoteDataSource {
    override suspend fun getPopular(): MovieModel {
        return retrofitService.service.getPopular()
    }

    override suspend fun getUpcoming():MovieModel {
        return retrofitService.service.getUpcoming()
    }

    override suspend fun getMovieDetails(movie_id: String): MovieDetails {
        return retrofitService.service.getMovieDetails(movie_id)
    }

    override suspend fun searchMovies(query: String): MovieModel {
        return retrofitService.service.searchMovies(searchQuery = query)
    }
}