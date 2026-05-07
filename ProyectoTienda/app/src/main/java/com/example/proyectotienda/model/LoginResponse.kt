package com.example.proyectotienda.model

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)