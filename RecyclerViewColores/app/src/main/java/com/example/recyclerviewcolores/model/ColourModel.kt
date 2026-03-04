package com.example.recyclerviewcolores.model

class ColourModel {

    /*creamos la lista de colores*/
    fun getColores(): MutableList<Colour> {
        return mutableListOf(
            Colour("Rojo", "#F44336"),
            Colour("Verde", "#4CAF50"),
            Colour("Azul", "#2196F3"),
            Colour("Amarillo", "#FFEB3B"),
            Colour("Morado", "#9C27B0"),
            Colour("Naranja", "#FF9800"),
            Colour("Gris", "#9E9E9E"),
            Colour("Cian", "#00BCD4"),
            Colour("Verde azulado", "#00796B"),
            Colour("Uva", "#4A148C"),
            Colour("Coral", "#FF7F50"),
            Colour("Rosa", "#E91E63"),
            Colour("Marrón", "#795548"),
            Colour("Negro", "#000000"),
            Colour("Oro", "#FFD700"),
            Colour("Plateado", "#C0C0C0"),
            Colour("Bronce", "#CD7F32"),
        )
    }

    public suspend fun retornarLista() : Datos {
        return Datos("ok", getColores())
    }

}