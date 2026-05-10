package com.example.proyectotienda.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectotienda.api.RetrofitApi
import com.example.proyectotienda.model.Product
import com.example.proyectotienda.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: ProductRepository = ProductRepository(RetrofitApi.apiService)
) : ViewModel() {
    private val _productDetail = MutableLiveData<Product?>()
    val productDetail: LiveData<Product?> = _productDetail
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadProductDetail(token: String, productId: Long) {
        viewModelScope.launch {
            try {
                val product = repository.getProductById(token, productId)
                _productDetail.value = product
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar detalle: ${e.message}"
                _productDetail.value = null
            }
        }
    }

}