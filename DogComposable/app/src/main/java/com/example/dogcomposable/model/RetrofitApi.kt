package com.example.dogcomposable.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//objeto personalizado que el profe sugirio
class RetrofitApi {
    private val BASE_URL = "https://dog.ceo/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: DogApiService = retrofit.create(DogApiService::class.java)
}