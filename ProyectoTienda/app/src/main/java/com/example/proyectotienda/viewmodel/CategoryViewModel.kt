package com.example.proyectotienda.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectotienda.api.RetrofitApi
import com.example.proyectotienda.model.Category
import com.example.proyectotienda.repository.CategoryRepository
import kotlinx.coroutines.launch

//logica de negocio a la hora de utilizar la api
class CategoryViewModel(
    private val repository: CategoryRepository = CategoryRepository(RetrofitApi.apiService)
) : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadCategories(token: String) {
        viewModelScope.launch {
            try {
                val list = repository.getCategories(token)
                _categories.value = list
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar categorías: ${e.message}"
                _categories.value = emptyList()
            }
        }
    }
}
