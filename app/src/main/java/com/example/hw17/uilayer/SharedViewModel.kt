package com.example.hw17.uilayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw17.Repository
import com.example.hw17.localmodel.DetailedMovie
import com.example.hw17.localmodel.Movie
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: Repository) : ViewModel() {
    private val _movieLiveData = MutableLiveData<List<Movie>>()
    val movieLiveData: LiveData<List<Movie>> get() = _movieLiveData
    private val _movieDetailsLiveData = MutableLiveData<DetailedMovie>()
    val movieDetailsLiveData: LiveData<DetailedMovie> get() = _movieDetailsLiveData
    private val _upcomingMovieLiveData = MutableLiveData<List<Movie>>()
    val upcomingMovieLiveData: LiveData<List<Movie>> get() = _upcomingMovieLiveData

    fun getMoviesFromNet() = viewModelScope.launch {
        _movieLiveData.postValue(repository.getMoviesFromNet())
    }
    fun getMoviesFromDatabase() = viewModelScope.launch {
        _movieLiveData.postValue(repository.getMoviesFromDatabase())
    }
    fun getUpcoming() = viewModelScope.launch {
        _upcomingMovieLiveData.postValue(repository.getUpcoming())
    }

    fun getMovieDetails(movie_id: String) = viewModelScope.launch {
        _movieDetailsLiveData.postValue(repository.getMovieDetails(movie_id))
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        _movieLiveData.postValue(repository.searchMovies(query))
    }

}