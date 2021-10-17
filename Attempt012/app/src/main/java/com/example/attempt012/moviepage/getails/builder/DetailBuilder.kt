package com.example.attempt012.moviepage.getails.builder

import android.util.Log
import com.example.attempt012.moviepage.getails.api.DetailApi
import com.example.attempt012.moviepage.getails.model.DataDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailBuilder {


    val api_key = "f1c1fa32aa618e6adc168c3cc3cc6c46"
    val language = "ru"
    val BASE_URL = "https://api.themoviedb.org/"


    fun call_data(movie_id: Int) : Call<DataDetails> {

        var retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api : DetailApi = retrofit.create(DetailApi::class.java)

        var mycall: Call<DataDetails> = api.getData(movie_id, api_key, language)

        return mycall
    }

}