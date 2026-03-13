package com.example.apidogview.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainState {
    val retrofitApi = RetrofitApi()
    lateinit var fotosCargadas : DogResponse
    lateinit var misDatos: Datos
    /* suspend fun recuperaFotos (raza: String):
            DogResponse = withContext(Dispatchers.IO) {
        val respuesta = retrofitApi.retrofitService.getFotoPerros(raza)
        if (respuesta.isSuccessful) {
            respuesta.body()?.let {
                DogResponse(it.status, it.message)
            } ?: DogResponse("error", null)
        } else {
            DogResponse("error", null)
        }
    } */

    suspend fun recuperarFotosPag (raza: String): Datos = withContext(Dispatchers.IO) {
        val respuesta = retrofitApi.retrofitService.getFotoPerros(raza)

        if (respuesta.isSuccessful) {
            fotosCargadas = DogResponse(respuesta.body()!!.status, respuesta.body()!!.message)

            if (fotosCargadas.message!!.isNotEmpty()) {
                var numPags : Int = fotosCargadas.message!!.size / 10
                if (fotosCargadas.message!!.size % 10 != 0) numPags++

                misDatos = Datos(fotosCargadas.status, numPags, 1, mutableListOf())

                val rango = Math.min(fotosCargadas.message!!.size - 1, 9)
                for (i in 0..rango) {
                    misDatos.list.add(fotosCargadas.message!!.get(i))
                }
            }
            misDatos // Devolvemos el objeto
        } else {
            misDatos = Datos("error", null, null, mutableListOf())
            misDatos
        }
    }

    suspend fun scrollFotos(): Datos = withContext(Dispatchers.Default) {
        val inicio = misDatos.pagActual!! * 10
        misDatos.pagActual = misDatos.pagActual!! + 1

        val fin: Int
        // verificar si es la última pág o no
        if (misDatos.pagActual!! < misDatos.numPags!!) {
            fin = (misDatos.pagActual!! * 10) - 1
        } else {
            // si es la última, el final es el último elemento de la lista original
            fin = (fotosCargadas.message!!.size - 1)
        }
        for (i in inicio..fin) {
            misDatos.list.add(fotosCargadas.message!!.get(i))
        }

        misDatos
    }
}