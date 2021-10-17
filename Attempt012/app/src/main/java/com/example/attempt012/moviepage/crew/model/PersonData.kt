package com.example.attempt012.moviepage.crew.model


data class PersonData(
    var adult: Boolean,
    var gender: Int,
    var id: Int,
    var known_for_department: String,
    var name: String,
    var original_name: String,
    var popularity: Float,
    var profile_path: String,
    var cast_id: Int,
    var character: String,
    var credit_id: String,
    var order: Int,
    var job: String
)
