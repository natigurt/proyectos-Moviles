package com.example.calculadora_v2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadora_v2.model.CalculadoraModel
import com.example.calculadora_v2.model.Datos
import kotlinx.coroutines.launch

class CalculadoraViewModel : ViewModel() {

    private val miModelo = CalculadoraModel()

    private val _misDatos = MutableLiveData<Datos>(Datos())
    val misDatosObservables: LiveData<Datos> get() = _misDatos

    private var primerNumero = 0.0
    private var operacionActual = ""

    fun onNumeroClick(digito: String) {
        val estadoActual = _misDatos.value ?: Datos()

        //si es nuevo calculo, limpiamos
        val nuevoNumero = if (estadoActual.nuevoCalculo) {
            digito
        } else {
            estadoActual.numeroActual + digito
        }

        _misDatos.value = estadoActual.copy(
            numeroActual = nuevoNumero,
            nuevoCalculo = false,
            mostrarToast = false // reset de avisos
        )
    }

    fun onOperacionClick(op: String) {
        val estadoActual = _misDatos.value ?: Datos()

        //si le das 2 veces a una operación sin números nuevos
        if (estadoActual.numeroActual.isEmpty() || estadoActual.numeroActual == "0") {
            _misDatos.value = estadoActual.copy(mostrarToast = true)
            return
        }

        primerNumero = estadoActual.numeroActual.toDouble()
        operacionActual = op

        // actualizamos historial arriba y preparamos para el siguiente número
        _misDatos.value = estadoActual.copy(
            historial = "${estadoActual.numeroActual} $op",
            numeroActual = "",
            operacionPendiente = op,
            nuevoCalculo = false,
            mostrarToast = false
        )
    }

    fun onEquals() {
        val estadoActual = _misDatos.value ?: Datos()
        val segundoNumero = estadoActual.numeroActual.toDoubleOrNull() ?: 0.0

        if (operacionActual.isNotEmpty()) {
            viewModelScope.launch {

                val resultado = miModelo.calcular(primerNumero, segundoNumero, operacionActual)

                _misDatos.value = estadoActual.copy(
                    numeroActual = resultado.toString(),
                    historial = "${estadoActual.historial} ${estadoActual.numeroActual} =", //historial = "${estadoActual.historial} $segundoNumero =",
                    nuevoCalculo = true,
                    operacionPendiente = ""
                )
                operacionActual = ""
            }
        }
    }

    fun onClear() {
        // btn clear sirve para empezar nuevo cálculo
        primerNumero = 0.0
        operacionActual = ""
        _misDatos.value = Datos()
    }

    fun toastMostrado() {
        _misDatos.value = _misDa tos.value?.copy(mostrarToast = false)
    }

}