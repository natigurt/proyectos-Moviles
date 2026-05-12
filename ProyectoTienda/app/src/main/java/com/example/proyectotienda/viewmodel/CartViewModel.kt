package com.example.proyectotienda.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectotienda.api.RetrofitApi
import com.example.proyectotienda.model.Cart
import com.example.proyectotienda.repository.CartRepository
import com.example.proyectotienda.repository.ProductRepository
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository = CartRepository(RetrofitApi.apiService)
) : ViewModel() {

    private val _cart = MutableLiveData<Cart?>()
    val cart: LiveData<Cart?> = _cart

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadCart(token: String) {
        viewModelScope.launch {
            try {
                val tempCart = repository.getCart(token)
                _cart.value = tempCart

            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar: ${e.message}"
            }
        }
    }

    // aniadir item al carrito
    fun addItemToCart(token: String, productId: Long, units: Int = 1) {
        viewModelScope.launch {
            try {
                repository.addToCart(token, productId, units)
                loadCart(token)
            } catch (e: Exception) {
                _errorMessage.value = "No se pudo añadir: ${e.message}"
            }
        }
    }

    // eliminar item del carrito
    fun deleteItemFromCart(token: String, productId: Long) {
        viewModelScope.launch {
            try {
                repository.deleteItem(token, productId)
                loadCart(token)
            } catch (e: Exception) {
                _errorMessage.value = "Error al borrar: ${e.message}"
            }
        }
    }
}
