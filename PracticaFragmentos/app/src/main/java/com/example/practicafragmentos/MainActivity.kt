package com.example.practicafragmentos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.practicafragmentos.databinding.ActivityMainBinding
import com.example.practicafragmentos.fragments.FragmentoBisiesto
import com.example.practicafragmentos.fragments.FragmentoDivisible
import com.example.practicafragmentos.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //configurar toolbar
        setSupportActionBar(binding.toolbar);

        //numero generado
        viewModel.misDatos.observe(this) { datos ->
            binding.numGenerado.text = if (datos.numGenerado == 0) "0" else datos.numGenerado.toString()
        }

        //boton para generar num
        binding.btnNum.setOnClickListener {
            viewModel.generarNumero()
            val fragmento = if (binding.tabLayout.selectedTabPosition == 0)
                FragmentoBisiesto() else FragmentoDivisible()
            cargarFragmento(fragmento)
        }

        //cambio de pestanyas
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (viewModel.misDatos.value?.numGenerado == 0) {
                    Toast.makeText(this@MainActivity, "Genera un número primero", Toast.LENGTH_SHORT).show()
                    return
                }
                when (tab?.position) {
                    0 -> {
                        cargarFragmento(FragmentoBisiesto())
                        supportActionBar?.title = "Bisiesto"
                    }
                    1 -> {
                        cargarFragmento(FragmentoDivisible())
                        supportActionBar?.title = "Divisible"
                    }
                }
            }

            //si los quito no va bien el listener
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    private fun cargarFragmento(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragment)
            .commit()
    }


}