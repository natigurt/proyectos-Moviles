package com.example.dogcomposable.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogcomposable.domain.BreedItem
import com.example.dogcomposable.repository.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedsViewModel (private val repository: DogRepository) : ViewModel() {
    private val _breeds = MutableStateFlow<List<BreedItem>>(emptyList())
    val breeds: StateFlow<List<BreedItem>> = _breeds.asStateFlow()

    private val _photos = MutableStateFlow<List<String>>(emptyList())
    val photos: StateFlow<List<String>> = _photos.asStateFlow()

    init {
        loadBreeds()
    }

    private fun loadBreeds() {
        viewModelScope.launch {
            _breeds.value = repository.getBreedsList()
        }
    }

    fun loadPhotos(breed: String, subBreed: String?) {
        viewModelScope.launch {
            _photos.value = repository.getPhotos(breed, subBreed)
        }
    }
}
