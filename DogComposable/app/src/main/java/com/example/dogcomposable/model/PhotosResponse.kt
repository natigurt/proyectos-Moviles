package com.example.dogcomposable.model

//respuesta de la api para obtener las fotos de una raza esp.
data class PhotosResponse (
    val message: List<String>, //para la lista de urls de fotos
    val status: String
)