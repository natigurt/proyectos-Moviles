package com.example.dogcomposable.model

//estructura exacta que devuelve la api. para deserializar el json.
data class BreedsResponse (
    val message: Map<String, List<String>>,
    val status: String
)