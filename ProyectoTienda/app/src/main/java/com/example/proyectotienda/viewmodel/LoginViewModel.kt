package com.example.proyectotienda.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectotienda.model.LoginResponse
import com.example.proyectotienda.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = AuthRepository();

    val loginResult = MutableLiveData<LoginResponse?>()
    val error = MutableLiveData<String>();
    val isLoading = MutableLiveData(false)

    fun login(email: String, password: String) {
        isLoading.value = true;
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful && response.body() != null) {
                    loginResult.value = response.body()
                } else {
                    error.value = "Error: ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                error.value= "Error de conexion > ${e.message}"
            } finally {
                isLoading.value = false;
            }
        }
    }
}