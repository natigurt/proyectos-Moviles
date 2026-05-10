package com.example.proyectotienda.repository

import com.example.proyectotienda.api.ApiService
import com.example.proyectotienda.model.Category

class CategoryRepository (private val apiService: ApiService) {

    suspend fun getCategories(token: String): List<Category> {
        return apiService.getCategories("Bearer $token")
    }

}