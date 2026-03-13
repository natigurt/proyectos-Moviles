package com.example.apidogview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apidogview.model.Datos
import com.example.apidogview.model.MainState
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val myState = MainState()

    private val _datos = MutableLiveData<Datos>()

    val datos : LiveData<Datos> get() = _datos

    fun devuelveFotos (raza: String) {
        viewModelScope.launch {
            _datos.value = myState.recuperarFotosPag(raza)
        }
    }

    fun scrollFotos () {
        viewModelScope.launch {
            _datos.value = myState.scrollFotos()
        }
    }
}