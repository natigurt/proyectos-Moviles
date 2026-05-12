package com.example.proyectotienda.model

data class CartItem(
    val id: Long,
    val productId: Long,
    val productName: String?,
    val unitPrice: Double,
    val units: Int,
    val subtotal: Double
)