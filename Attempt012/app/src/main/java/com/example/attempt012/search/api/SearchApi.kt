package com.example.attempt012.search.api

import com.example.attempt012.search.model.SearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/3/search/movie")
    fun getData(
        @Query("api_key") api_key: String,
        @Query("language") language : String,
        @Query("query") query : String,
        @Query("page") page : Int
    ) : Call<SearchModel>
}