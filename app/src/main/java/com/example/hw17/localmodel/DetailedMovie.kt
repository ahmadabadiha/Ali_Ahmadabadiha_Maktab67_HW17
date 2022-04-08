package com.example.hw17.localmodel

import com.example.hw17.servermodel.details.Genre
import com.example.hw17.servermodel.details.ProductionCompany
import com.example.hw17.servermodel.details.ProductionCountry

data class DetailedMovie(
    val id: String,
    val title: String,
    val overview: String,
    val voteAverage: String,
    val backdrop_path: String?,
    val budget: String,
    val genres: List<String>,
    val production_companies: List<String>,
    val production_countries: List<String>,
    val release_date: String,
    val runtime: String
)