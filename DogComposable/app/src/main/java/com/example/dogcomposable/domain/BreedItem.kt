package com.example.dogcomposable.domain

data class BreedItem(
    val breed: String,
    val subBreeds: List<String> = emptyList()
) {
    val dogName: String get() = breed.replaceFirstChar { it.uppercase() }
}
