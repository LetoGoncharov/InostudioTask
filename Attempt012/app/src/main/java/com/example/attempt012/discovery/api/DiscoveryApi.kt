package com.example.attempt012.discovery.api

import com.example.attempt012.discovery.model.DiscoveryResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoveryApi {
    @GET("3/discover/movie")
    fun getData(
        @Query("api_key") api_key: String,
        @Query("language") language : String,
        @Query("page") page : Int
    ) : Call<DiscoveryResults>
}