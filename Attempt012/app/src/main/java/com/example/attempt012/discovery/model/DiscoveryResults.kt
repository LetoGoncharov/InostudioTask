package com.example.attempt012.discovery.model

import com.google.gson.annotations.SerializedName

data class DiscoveryResults(
    val page: Int,
    val results: List<MovieList>,
    val total_results: Int,
    val total_pages: Int,
)