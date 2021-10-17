package com.example.attempt012.discovery.builder

import com.example.attempt012.discovery.api.DiscoveryApi
import com.example.attempt012.discovery.model.DiscoveryResults
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiscoveryBuilder {

    val api_key = "f1c1fa32aa618e6adc168c3cc3cc6c46"
    val language = "ru"
    val BASE_URL = "https://api.themoviedb.org/"


    fun call_data(page: Int) : Call<DiscoveryResults> {

        var retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api : DiscoveryApi = retrofit.create(DiscoveryApi::class.java)

        var mycall: Call<DiscoveryResults> = api.getData(api_key, language, page)

        return mycall
    }
}