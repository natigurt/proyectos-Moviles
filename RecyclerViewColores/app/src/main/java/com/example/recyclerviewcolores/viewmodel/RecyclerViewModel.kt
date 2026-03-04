package com.example.recyclerviewcolores.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recyclerviewcolores.model.Colour
import com.example.recyclerviewcolores.model.ColourModel
import com.example.recyclerviewcolores.model.Datos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecyclerViewModel : ViewModel() {

    var miModelo = ColourModel()
    private var _datos =  MutableStateFlow<Datos>(Datos("", mutableListOf<Colour>()))

    val datos: StateFlow<Datos> get() = _datos

    public fun retornarLista() {
        viewModelScope.launch {
            _datos.value = miModelo.retornarLista();
        }
    }
}