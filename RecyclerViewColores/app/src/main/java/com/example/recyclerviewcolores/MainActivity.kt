package com.example.recyclerviewcolores

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewcolores.databinding.ActivityMainBinding
import com.example.recyclerviewcolores.model.Datos
import com.example.recyclerviewcolores.recycler.MiAdaptador
import com.example.recyclerviewcolores.viewmodel.RecyclerViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var miAdaptador : MiAdaptador
    private val miViewModel : RecyclerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        //inicializar adaptador
        miAdaptador = MiAdaptador(Datos("", mutableListOf()))

        //configurar RecyclerView
        binding.myRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.myRecyclerView.adapter = miAdaptador

        //cargar datos
        miViewModel.retornarLista()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                miViewModel.datos.collect { nuevosDatos ->
                    when (nuevosDatos.estado) {
                        "ok" -> {
                            miAdaptador.misDatos = nuevosDatos
                            miAdaptador.notifyDataSetChanged() //refrescar la lista.
                        }
                        "error" -> {
                            Toast.makeText(this@MainActivity, "Error al cargar los colores!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }
}