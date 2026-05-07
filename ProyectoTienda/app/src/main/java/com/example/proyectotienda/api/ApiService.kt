package com.example.proyectotienda.api

import com.example.proyectotienda.model.LoginRequest
import com.example.proyectotienda.model.LoginResponse
import com.example.proyectotienda.model.ProductPag
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    //conseguir productitos
    @GET("api/v1/products")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ProductPag

}

