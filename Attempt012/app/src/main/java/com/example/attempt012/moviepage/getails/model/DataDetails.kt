package com.example.attempt012.moviepage.getails.model

import java.util.*

data class DataDetails(
    var title: String,
    var poster_path: String,
    var tagline: String,
    var budget: Long,
    var original_title: String,
    var release_date: String,
    var vote_average: Float,
    var overview: String,


    // less required
    var adult: Boolean,
    var backdrop_path: String,
    var belongs_to_collection: Objects,
    var genres: List<Objects>,
    var homepage: String,
    var id: Int,
    var imdb_id: String,
    var original_language: String,
    var popularity: Float,
    var production_companies: List<Objects>,
    var production_countries: List<Objects>,
    var revenue: Long,
    var runtime: Long,
    var spoken_languages: List<Objects>,
    var status: String,
    var video: Boolean,
    var vote_count: Int
)
