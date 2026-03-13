package com.example.apidogview.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//creamos un objeto para facilitar el entendimiento!
class RetrofitApi {

    val retrofitBase = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/breed/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService = retrofitBase.create(DogAPIService::class.java)

}