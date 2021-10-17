package com.example.attempt012.search.model

import com.example.attempt012.discovery.model.MovieList

data class SearchModel(
    val page: Int,
    val results: List<MovieList>,
    val total_results: Int,
    val total_pages: Int,
)
