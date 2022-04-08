package com.example.hw17.localmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    val id: String,
    val title: String,
    val overview: String,
    val voteAverage: String,
    val imagePath: String?
)
