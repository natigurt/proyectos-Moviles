package com.example.proyectotienda.model


data class Cart (
    val items: List<CartItem>,
    val distinctProducts: Long,
    val totalUnits: Long,
    val totalPrice: Double
)