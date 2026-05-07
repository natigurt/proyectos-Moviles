package com.example.proyectotienda.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.proyectotienda.model.Product
import com.example.proyectotienda.repository.ProductRepository

class ProductsViewModel
{
    private val repository = ProductRepository();
    val products = MutableLiveData<List<Product>>()
    val totalPags = MutableLiveData<Int>()
    val actualPag = MutableLiveData<Int>()

    /*
    fun loadProducts(token: String, page: Int, size: Int) {
        viewModelScope.launch {
            try {

            }
        }
    } */

}