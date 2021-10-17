package com.example.attempt012.moviepage.getails.api

import com.example.attempt012.discovery.model.DiscoveryResults
import com.example.attempt012.moviepage.getails.model.DataDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {
    @GET("3/movie/{movie_id}")
    fun getData(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language : String
    ) : Call<DataDetails>
}