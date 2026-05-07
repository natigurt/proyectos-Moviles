package com.example.proyectotienda.repository

import com.example.proyectotienda.api.RetrofitApi
import com.example.proyectotienda.model.LoginRequest
import com.example.proyectotienda.model.LoginResponse
import retrofit2.Response

class AuthRepository {

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return RetrofitApi.apiService.login(LoginRequest(email, password))
    }

}