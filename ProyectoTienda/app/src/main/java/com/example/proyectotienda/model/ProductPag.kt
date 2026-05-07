package com.example.proyectotienda.model

data class ProductPag (
    val totalPags: Int,
    val totalProducts: Int,
    val size: Int,
    val products: List<Product>
)