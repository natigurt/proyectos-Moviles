package com.example.proyectotienda.api

import com.example.proyectotienda.model.Cart
import com.example.proyectotienda.model.CartRequest
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

interface ApiService {

    // autenticacion
    @POST("api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // productos
    @GET("api/v1/products")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): List<Product>

    // categorias
    @GET("api/v1/categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): List<Category>

    // productos por categoria
    @GET("api/v1/categories/{categoryId}/products")
    suspend fun getProductsByCategory(
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Long
    ): List<Product>

    // detalle producto
    @GET("api/v1/products/{productId}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("productId") productId: Long
    ): Product

    // carrito
    @GET("api/v1/cart")
    suspend fun getCart(@Header("Authorization") token: String): Cart

    // aniadir al carrito
    @POST("api/v1/cart")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body request: CartRequest
    ): Cart

    // eliminar de carrito
    @DELETE("api/v1/cart/{productId}")
    suspend fun deleteCartItem(
        @Header("Authorization") token: String,
        @Path("productId") productId: Long
    ): Cart
}
