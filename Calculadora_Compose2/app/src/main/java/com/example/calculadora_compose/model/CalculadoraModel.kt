package com.example.calculadora_compose.model

class CalculadoraModel {

    fun calcular (n1: Double, n2:Double, op: String) : Double {

        return when (op) {
            "+" -> n1 + n2
            "-" -> n1 - n2
            "*" -> n1 * n2
            "/" -> if (n2 != 0.0) n1 / n2 else Double.NaN //para evitar el crasheo en algunas ops
            else -> 0.0
        }

    }

}