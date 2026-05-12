package com.example.proyectotienda.repository

import com.example.proyectotienda.api.ApiService
import com.example.proyectotienda.model.Cart
import com.example.proyectotienda.model.CartRequest

class CartRepository(private val apiService: ApiService) {

    suspend fun getCart(token: String): Cart {
        return apiService.getCart("Bearer $token")
    }

    suspend fun addToCart(token: String, productId: Long, units: Int): Cart {
        // crear el objeto cartRequest con los datos necesarios para la peticion
        val request = CartRequest(
            productId = productId,
            units = units
        )
        return apiService.addToCart("Bearer $token", request)
    }
    suspend fun deleteItem(token: String, productId: Long): Cart {
        return apiService.deleteCartItem("Bearer $token", productId)
    }
}
