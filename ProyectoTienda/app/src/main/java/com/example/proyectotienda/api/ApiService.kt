package com.example.proyectotienda.api

import com.example.proyectotienda.model.Category
import com.example.proyectotienda.model.LoginRequest
import com.example.proyectotienda.model.LoginResponse
import com.example.proyectotienda.model.Product
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

    //conseguir categorias
    @GET("api/v1/categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): List<Category>

    //conseguir los productos de esas categorias.
    @GET("api/v1/categories/{categoryId}/products")
    suspend fun getProductsByCategory(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Long
    ): List<Product>

    //conseguir detalle producto
    @GET("api/v1/products/{productId}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("productId") productId: Long
    ): Product

}



