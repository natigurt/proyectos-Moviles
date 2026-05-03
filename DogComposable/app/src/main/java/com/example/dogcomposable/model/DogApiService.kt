package com.example.dogcomposable.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {

    @GET("breeds/list/all")
    suspend fun getAllBreeds() : Response<BreedsResponse>;

    @GET("breed/{breed}/images")
    suspend fun getPhotosByBreed(@Path("breed") breed: String): Response<PhotosResponse>

    @GET("breed/{breed}/{subBreed}/images")
    suspend fun getPhotosBySubBreed(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): Response<PhotosResponse>

}