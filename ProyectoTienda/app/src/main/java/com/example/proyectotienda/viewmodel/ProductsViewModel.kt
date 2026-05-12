package com.example.proyectotienda.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectotienda.api.RetrofitApi
import com.example.proyectotienda.model.Category
import com.example.proyectotienda.model.Product
import com.example.proyectotienda.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val repository: ProductRepository = ProductRepository(RetrofitApi.apiService)
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadCategories(token: String) {
        viewModelScope.launch {
            try {
                val list = repository.getCategories(token)
                // insertar categoría
                val finalList = mutableListOf<Category>().apply {
                    add(Category(id = -1, name = "Todos los productos", description = "", image = ""))
                    addAll(list)
                }
                _categories.value = finalList
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar categorías: ${e.message}"
                _categories.value = emptyList()
            }
        }
    }

    fun loadAllProducts(token: String) {
        viewModelScope.launch {
            try {
                val list = repository.getProducts(token)
                _products.value = list
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar productos: ${e.message}"
                _products.value = emptyList()
            }
        }
    }

    fun loadProductsByCategory(token: String, categoryId: Long) {
        viewModelScope.launch {
            try {
                val list = repository.getProductsByCategory(token, categoryId)
                _products.value = list
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar productos: ${e.message}"
                _products.value = emptyList()
            }
        }
    }
}
