package com.example.proyectotienda.model

data class Product(
    val id: Long,
    val productCode: String,
    val name: String,
    val description: String,
    val image: String?,
    val price: Double,
    val discount: Int,
    val stock: Int,
    val categories: List<Category>?
)