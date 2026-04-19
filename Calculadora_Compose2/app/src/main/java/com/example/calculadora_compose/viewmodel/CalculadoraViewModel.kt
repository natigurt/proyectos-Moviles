package com.example.calculadora_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadora_compose.model.CalculadoraModel
import com.example.calculadora_compose.model.Datos
import kotlinx.coroutines.launch

class CalculadoraViewModel : ViewModel() {

    private val miModelo = CalculadoraModel()
    private val _misDatos = MutableLiveData<Datos>(Datos("", "", "0.0"))
    val misDatosObservables: LiveData<Datos> get() = _misDatos;

    fun actualizarNums (valor: String, esPrimero: Boolean) {
        val datosActuales = _misDatos.value ?: Datos("", "", "0.0")

        _misDatos.value = if (esPrimero) {
            datosActuales.copy(primerNum = valor, resultado = "0.0")
        } else {
            datosActuales.copy(segundoNum = valor, resultado = "0.0")
        }
    }

    fun operar(operador: String) {
        val estadoActual = _misDatos.value;

        if (estadoActual != null) {
            if (estadoActual.primerNum.isEmpty() || estadoActual.segundoNum.isEmpty()) {
                _misDatos.value = estadoActual.copy(resultado = "Hay campos vacíos!")
                return
            }
        }

        val num1 = estadoActual.primerNum.toDoubleOrNull()
        val num2 = estadoActual.segundoNum.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            _misDatos.value = estadoActual.copy(resultado = "Numeros invalidos!!")
            return
        }

        viewModelScope.launch {
            val resultado = miModelo.calcular(num1, num2, operador)
            _misDatos.value = estadoActual.copy(resultado = cambiarResultado(resultado))
        }
    }

    private fun cambiarResultado(resultado: Double): String {
        return if (resultado == resultado.toLong().toDouble()) {
            resultado.toLong().toString()
        } else {
            "%.4f".format(resultado).trimEnd('0').trimEnd('.')
        }
    }

}