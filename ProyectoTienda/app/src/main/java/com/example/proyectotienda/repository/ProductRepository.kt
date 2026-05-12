package com.example.proyectotienda.repository

import com.example.proyectotienda.api.ApiService
import com.example.proyectotienda.model.Category
import com.example.proyectotienda.model.Product

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProducts(token: String): List<Product> {
        return apiService.getProducts("Bearer $token")
    }

    suspend fun getCategories(token: String): List<Category> {
        return apiService.getCategories("Bearer $token")
    }

    suspend fun getProductsByCategory(token: String, categoryId: Long): List<Product> {
        return apiService.getProductsByCategory("Bearer $token", categoryId)
    }

    suspend fun getProductById(token: String, productId: Long): Product {
        return apiService.getProductById("Bearer $token", productId)
    }

}
