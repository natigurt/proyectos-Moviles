package com.example.aniobisiesto_practica
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    //lateinit es guardar un hueco para esa variable porque todavia no la aplicacion aun no la va a encontrar
    private lateinit var tvNumero: TextView
    private lateinit var txtResultado: TextView
    private lateinit var rbSi: RadioButton
    private lateinit var rbNo: RadioButton
    private lateinit var swFondoY: Switch
    private lateinit var fondoPrincipal: View
    private var anioActual: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // variables
        tvNumero = findViewById(R.id.numberText)
        txtResultado = findViewById(R.id.resText)
        rbSi = findViewById(R.id.rdBtnY)
        rbNo = findViewById(R.id.rdBtnN)

        val btnGenerar = findViewById<Button>(R.id.btnNumber)
        val btnComprobar = findViewById<Button>(R.id.btnRes)
        swFondoY = findViewById(R.id.switch1)
        fondoPrincipal = findViewById(R.id.main)

        // switch del fondo utilizar setOnCheckedChangeListener para cambiar de on a off.
        swFondoY.setOnCheckedChangeListener { _, isChecked ->
            cambiarFondo(isChecked, fondoPrincipal)
        }

        btnGenerar.setOnClickListener {
            generarNumAleatorio()
        }

        btnComprobar.setOnClickListener {
            generarRespuesta()
        }
    }

    //si esta activado, se cambia el color. o tal vez que cuando se produzca algun cambio, cambie el fondo.
    fun cambiarFondo(activo : Boolean, vista : View) {
        if (activo) {
            val amarillo = ContextCompat.getColor(this, R.color.yellow)
            vista.setBackgroundColor(amarillo)
        } else {
            val blanco = ContextCompat.getColor(this, R.color.white)
            vista.setBackgroundColor(blanco)
        }
    }

    fun generarNumAleatorio() {
        anioActual = (1900..2500).random()
        tvNumero.text = anioActual.toString();
        txtResultado.text = ""
    }

    fun generarRespuesta() {
        val resultadoBisiesto = esAnioBisiesto(anioActual)

        if (!rbSi.isChecked && !rbNo.isChecked) {
            txtResultado.text = "Selecciona una opción"
        } else if ((resultadoBisiesto && rbSi.isChecked) || (!resultadoBisiesto && !rbSi.isChecked)) {
            txtResultado.text = "Correcto"
            val verde = ContextCompat.getColor(this, R.color.green)
            txtResultado.setTextColor(verde)
        } else {
            txtResultado.text = "Incorrecto"
            val rojo = ContextCompat.getColor(this, R.color.red)
            txtResultado.setTextColor(rojo)
        }
    }

    fun esAnioBisiesto(anio: Int): Boolean {
       return (anioActual % 4 == 0 && anioActual % 100 != 0) || (anioActual % 400 == 0)
    }
}



