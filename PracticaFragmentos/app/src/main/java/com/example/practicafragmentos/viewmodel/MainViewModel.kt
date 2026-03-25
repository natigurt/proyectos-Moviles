package com.example.practicafragmentos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicafragmentos.model.Datos

class MainViewModel : ViewModel() {

    private val _datosApp = MutableLiveData<Datos>()
    val misDatos: LiveData<Datos> = _datosApp

    init {
        // evitamos nulos
        _datosApp.value = Datos("Pendiente", 0)
    }

    fun generarNumero() {
        val nuevoNum = (1900..2200).random()

        // actualizar los datos
        _datosApp.value = Datos(State = "Pendiente", numGenerado = nuevoNum)
    }

    fun validarBisiesto(usuarioBisiesto: Boolean) {
        val datosActuales = _datosApp.value ?: return //salimos de aqui si es nulo
        val num = datosActuales.numGenerado
        val esRealmenteBisiesto = (num % 4 == 0 && num % 100 != 0) || (num % 400 == 0)
        val esCorrecto = (usuarioBisiesto == esRealmenteBisiesto)

        datosActuales.State = if (esCorrecto) "Correcto" else "Incorrecto";

        _datosApp.value = datosActuales
    }

    fun validarDivisible(p2: Boolean, p3: Boolean, p5: Boolean, p10: Boolean, ninguno: Boolean) {
        val datosActuales = _datosApp.value ?: return
        val num = datosActuales.numGenerado

        // calculamos si es divisible por..
        val es2 = num % 2 == 0
        val es3 = num % 3 == 0
        val es5 = num % 5 == 0
        val es10 = num % 10 == 0
        val esNinguno = !es2 && !es3 && !es5 && !es10

        // acerto?
        val acerto = (p2 == es2 && p3 == es3 && p5 == es5 && p10 == es10 && ninguno == esNinguno)

        // actualizar objeto
        datosActuales.State = if (acerto) "Correcto" else "Incorrecto"
        _datosApp.value = datosActuales
    }


}