package com.example.hw17

import android.content.Context
import com.example.hw17.database.MovieDatabase
import com.example.hw17.datalayer.*

class AppContainer(context: Context) {
    private val retrofitService = RetrofitService()
    private val remoteDataSource: RemoteDataSource = RetrofitDataSource(retrofitService)
    private val movieDao = MovieDatabase.getDatabase(context).movieDao()
    private val localDataSource: LocalDataSource = RoomDataSource(movieDao)
    val repository = Repository(remoteDataSource, localDataSource)
}