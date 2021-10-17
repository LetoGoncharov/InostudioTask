package com.example.attempt012.moviepage.crew.api


import com.example.attempt012.moviepage.crew.model.CrewData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CrewApi {
    @GET("3/movie/{movie_id}/credits")
    fun getData(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language : String,
    ) : Call<CrewData>
}