package com.example.calculadora_v2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora_v2.databinding.ActivityMainBinding
import com.example.calculadora_v2.viewmodel.CalculadoraViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val miViewModel: CalculadoraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        miViewModel.misDatosObservables.observe(this) { datos ->
            binding.campoPrincipal.text = datos.numeroActual

            // mostrar numeros anteriores arriba
            binding.campoPasado.text = datos.historial

            //toast
            if (datos.mostrarToast) {
                Toast.makeText(this, "Elige algun numero", Toast.LENGTH_SHORT).show()
                miViewModel.toastMostrado() //cambiar el booleano
            }
        }

        val botonesNumeros = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        botonesNumeros.forEach { boton ->
            boton.setOnClickListener {
                //pasarle texto del num
                miViewModel.onNumeroClick(boton.text.toString())
            }
        }

        // botones para las operaciones
        binding.btnSumar.setOnClickListener { miViewModel.onOperacionClick("+") }
        binding.btnRestar.setOnClickListener { miViewModel.onOperacionClick("-") }
        binding.btnMultiplicar.setOnClickListener { miViewModel.onOperacionClick("*") }
        binding.btnDividir.setOnClickListener { miViewModel.onOperacionClick("/") }


        binding.btnEquals.setOnClickListener {
            miViewModel.onEquals()
        }

        binding.btnClear.setOnClickListener {
            miViewModel.onClear()
        }
    }
}