package com.example.calculadora_v2.model

class CalculadoraModel {

    fun calcular (n1: Double, n2: Double, op: String) : Double {
        return when (op) {
            "+" -> n1 + n2
            "-" -> n1 - n2
            "*" -> n1 * n2
            "/" -> if (n2 != 0.0) n1 / n2 else Double.NaN //Double.NaN evita el crash en algunas operaciones.
            else -> 0.0
        }
    }

}