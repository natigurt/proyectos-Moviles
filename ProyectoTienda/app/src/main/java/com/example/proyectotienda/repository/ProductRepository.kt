package com.example.proyectotienda.repository

import com.example.proyectotienda.api.RetrofitApi
import com.example.proyectotienda.model.ProductPag

class ProductRepository {

    suspend fun getProducts(token: String, page: Int, size: Int): ProductPag {
        return RetrofitApi.apiService.getProducts("Bearer $token", page, size)
    }

}