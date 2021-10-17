package com.example.attempt012.moviepage.photos.api

import com.example.attempt012.moviepage.photos.model.PhotoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoApi {
    @GET("3/movie/{movie_id}/images")
    fun getData(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language : String,
    ) : Call<PhotoData>
}