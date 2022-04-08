package com.example.hw17.servermodel

import com.example.hw17.servermodel.Result

data class MovieModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)