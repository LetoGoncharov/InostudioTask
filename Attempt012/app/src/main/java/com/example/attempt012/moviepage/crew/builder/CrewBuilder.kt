package com.example.attempt012.moviepage.crew.builder

import com.example.attempt012.moviepage.crew.api.CrewApi
import com.example.attempt012.moviepage.crew.model.CrewData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CrewBuilder {

    val api_key = "f1c1fa32aa618e6adc168c3cc3cc6c46"
    val language = "ru"
    val BASE_URL = "https://api.themoviedb.org/"

    fun call_data(movie_id: Int) : Call<CrewData> {

        var retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api : CrewApi = retrofit.create(CrewApi::class.java)

        var mycall: Call<CrewData> = api.getData(movie_id, api_key, language)

        return mycall
    }
}