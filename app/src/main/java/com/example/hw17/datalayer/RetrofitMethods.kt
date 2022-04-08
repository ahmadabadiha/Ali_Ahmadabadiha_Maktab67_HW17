package com.example.hw17.datalayer

import com.example.hw17.servermodel.MovieModel
import com.example.hw17.servermodel.details.MovieDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RetrofitMethods {
    @GET("movie/popular")
    suspend fun getPopular(
        @QueryMap query: Map<String, String> = mapOf(
            "api_key" to "af83fd22713a9cd5adc548887fcd9e29",
            "language" to "en-US",
            "page" to "1"
        )
    ): MovieModel

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @QueryMap query: Map<String, String> = mapOf(
            "api_key" to "af83fd22713a9cd5adc548887fcd9e29",
            "language" to "en-US",
            "page" to "15"
        )
    ): MovieModel

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: String,
        @QueryMap query: Map<String, String> = mapOf(
            "api_key" to "af83fd22713a9cd5adc548887fcd9e29",
            "language" to "en-US"
        )
    ): MovieDetails

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = "af83fd22713a9cd5adc548887fcd9e29",
        @Query("query") searchQuery: String
    ): MovieModel
/*
    @GET("https://image.tmdb.org/t/p/w500")
    suspend fun getImage(@Path ("imagePath") path: String)*/
}