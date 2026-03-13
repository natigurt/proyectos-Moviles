package com.example.apidogview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apidogview.databinding.ActivityMainBinding
import com.example.apidogview.model.Datos
import com.example.apidogview.model.DogResponse
import com.example.apidogview.recycler.MyAdapter
import com.example.apidogview.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var myAdapter: MyAdapter
    private lateinit var misDatos: Datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // Inicializar adaptador con DogResponse vacío
        val dogResponseVacio = DogResponse("", emptyList())
        myAdapter = MyAdapter(dogResponseVacio)

        val mLayout = LinearLayoutManager(this)
        binding.myRecyclerView.layoutManager = mLayout;
        binding.myRecyclerView.adapter = myAdapter

        /*scroll*/
        binding.myRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var fin = false;

                if (mLayout.findLastVisibleItemPosition() % 10 >= 9 &&
                    mLayout.findLastVisibleItemPosition() / 10 == (misDatos.pagActual!! - 1)) {
                    fin = true
                }
                //si todavia quedan por cargar..
                if (fin && misDatos.pagActual!! < misDatos.numPags!!) {
                    Snackbar.make(binding.root, "Hay mas fotos disponibles...", Snackbar.LENGTH_LONG)
                        .setAction("Cargar mas!!") {
                            viewModel.scrollFotos()
                        }.show()
                }
            }
        })

        viewModel.datos.observe(this) { datos ->
            misDatos = datos

            when (datos.state) {
                "success" -> {
                    if (datos.pagActual == 1) {
                        val respuestaInicial = DogResponse(datos.state, datos.list)
                        myAdapter = MyAdapter(respuestaInicial)
                        binding.myRecyclerView.adapter = myAdapter
                    } else {
                        myAdapter.dataSet = DogResponse(datos.state, datos.list)
                        val posicionInicio = (datos.pagActual!! - 1) * 10
                        myAdapter.notifyItemRangeInserted(posicionInicio, 10)
                    }
                }
                "error" -> {
                    Toast.makeText(this, "Error al cargar", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnCargar.setOnClickListener {
            val raza = binding.txtUser.text.toString().trim()
            if (raza.isNotEmpty()) {
                Toast.makeText(this, "Buscando imágenes de $raza", Toast.LENGTH_LONG).show()
                viewModel.devuelveFotos(raza)
            } else {
                Toast.makeText(this, "Por favor introduce una raza!", Toast.LENGTH_LONG).show()
            }
        }
    }
}