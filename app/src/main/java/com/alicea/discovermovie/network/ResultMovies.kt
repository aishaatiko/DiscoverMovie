package com.alicea.discovermovie.network

import com.alicea.discovermovie.database.Movie
data class ResultMovies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
