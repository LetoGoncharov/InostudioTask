package com.example.attempt012.search.builder


import com.example.attempt012.search.api.SearchApi
import com.example.attempt012.search.model.SearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchBuilder {
    val api_key = "f1c1fa32aa618e6adc168c3cc3cc6c46"
    val language = "ru"
    val BASE_URL = "https://api.themoviedb.org/"

    fun call_data(query: String, page: Int): Call<SearchModel> {

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api: SearchApi = retrofit.create(SearchApi::class.java)

        var searchcall: Call<SearchModel> = api.getData(api_key, language, query, page)

        return searchcall
    }
}