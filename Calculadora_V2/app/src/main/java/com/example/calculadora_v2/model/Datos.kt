package com.example.calculadora_v2.model

data class Datos (
    val historial: String = "", //texto de arriba
    val numeroActual: String = "0", //lo que se esta escribiendo
    val resultado: String = "",
    val operacionPendiente: String = "", //para saber si eligio mas, menos, etc..
    val nuevoCalculo: Boolean = true, //para saber si el prox num limpia la pantalla.
    val mostrarToast: Boolean = false
    )