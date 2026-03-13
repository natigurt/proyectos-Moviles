package com.example.apidogview.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogAPIService {

    @GET("{raza}/images")
    suspend fun getFotoPerros ( @Path("raza") raza: String) :
            Response<DogResponse>

}