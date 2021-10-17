package com.example.attempt012.moviepage.photos.builder

import com.example.attempt012.moviepage.photos.api.PhotoApi
import com.example.attempt012.moviepage.photos.model.PhotoData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotoBuilder {

    val api_key = "f1c1fa32aa618e6adc168c3cc3cc6c46"
    val language = "ru"
    val BASE_URL = "https://api.themoviedb.org/"

    fun call_data(movie_id: Int) : Call<PhotoData> {

        var retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api : PhotoApi = retrofit.create(PhotoApi::class.java)

        var mycall: Call<PhotoData> = api.getData(movie_id, api_key, language)

        return mycall
    }
}