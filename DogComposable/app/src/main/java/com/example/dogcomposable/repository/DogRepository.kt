package com.example.dogcomposable.repository

import com.example.dogcomposable.domain.BreedItem
import com.example.dogcomposable.model.DogApiService

//ayuda a transformar breedResponse en lista de breedItem
class DogRepository(val api: DogApiService) {

    suspend fun getBreedsList(): List<BreedItem> {
        val response = api.getAllBreeds()
        return if (response.isSuccessful && response.body() != null) {
            transformToBreedItems(response.body()!!.message)
        } else {
            emptyList()
        }
    }

    suspend fun getPhotos(breed: String, subBreed: String? = null): List<String> {
        val response = if (subBreed == null) {
            api.getPhotosByBreed(breed)
        } else {
            api.getPhotosBySubBreed(breed, subBreed)
        }
        return if (response.isSuccessful && response.body() != null) {
            response.body()!!.message
        } else {
            emptyList()
        }
    }

    private fun transformToBreedItems(message: Map<String, List<String>>): List<BreedItem> {
        return message.map { (breed, subBreeds) ->
            BreedItem(breed, subBreeds)
        }.sortedBy { it.breed }
    }
}
