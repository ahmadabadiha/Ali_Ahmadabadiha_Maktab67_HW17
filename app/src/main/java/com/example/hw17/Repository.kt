package com.example.hw17

import android.util.Log
import com.example.hw17.datalayer.LocalDataSource
import com.example.hw17.datalayer.RemoteDataSource
import com.example.hw17.localmodel.DetailedMovie
import com.example.hw17.localmodel.Movie
import com.example.hw17.servermodel.details.MovieDetails

class Repository(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) {


    suspend fun getMoviesFromNet():List<Movie>{

        val localMovieList: MutableList<Movie> = mutableListOf()
        remoteDataSource.getPopular().results.forEach { movieItem ->
            val movie = Movie(
                movieItem.id.toString(),
                movieItem.title,
                movieItem.overview,
                movieItem.vote_average.toString(),
                movieItem.poster_path
            )
            localMovieList.add(movie)
        }
        localDataSource.insertMovies(*localMovieList.toTypedArray())
        return localDataSource.getAllMovies()
    }

    suspend fun getMoviesFromDatabase():List<Movie>{
        Log.d("ali", "getMoviesFromDatabase: ")
        return localDataSource.getAllMovies()
    }
    suspend fun getUpcoming(): List<Movie> {
        val movieList: MutableList<Movie> = mutableListOf()
        remoteDataSource.getUpcoming().results.forEach { movieItem ->
            val movie = Movie(
                movieItem.id.toString(),
                movieItem.title,
                movieItem.overview,
                movieItem.vote_average.toString(),
                movieItem.poster_path
            )
            movieList.add(movie)
        }
        return movieList
    }

    suspend fun getMovieDetails(movie_id: String): DetailedMovie {
        val serverModel = remoteDataSource.getMovieDetails(movie_id)
        val genreList = serverModel.genres.map {
            it.name
        }
        val companyList = serverModel.production_companies.map {
            it.name
        }
        val countryList = serverModel.production_countries.map {
            it.name
        }
        return DetailedMovie(
            serverModel.id.toString(),
            serverModel.title,
            serverModel.overview,
            serverModel.vote_average.toString(),
            serverModel.backdrop_path,
            serverModel.budget.toString(),
            genreList,
            companyList,
            countryList,
            serverModel.release_date,
            serverModel.runtime.toString()
        )
    }

    suspend fun searchMovies(query: String): List<Movie> {
        val movieList: MutableList<Movie> = mutableListOf()
        remoteDataSource.searchMovies(query).results.forEach { movieItem ->
            val movie = Movie(
                movieItem.id.toString(),
                movieItem.title,
                movieItem.overview,
                movieItem.vote_average.toString(),
                movieItem.poster_path
            )
            movieList.add(movie)
        }
        return movieList
    }

}