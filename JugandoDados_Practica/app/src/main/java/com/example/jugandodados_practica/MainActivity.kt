package com.example.jugandodados_practica

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    /* elementos layout */
    private lateinit var desplegable: Spinner;
    private lateinit var btnGroup : MaterialButtonToggleGroup;
    private lateinit var btnDados : Button

    private lateinit var miAnimacionDados: AnimationDrawable
    private lateinit var imgContenedor: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        desplegable = findViewById(R.id.desplegable);
        btnGroup = findViewById(R.id.btnGroup)
        btnDados = findViewById(R.id.btnDados)
        val tvDado1 = findViewById<TextView>(R.id.dado1)
        val tvDado2 = findViewById<TextView>(R.id.dado2)
        val saldoActual = findViewById<TextView>(R.id.txtSaldo).text.toString().toDouble()
        val imgResultado = findViewById<ImageView>(R.id.imgResultado)
        imgContenedor = findViewById<ImageView>(R.id.imgContenedorAnimacion)
        imgContenedor.setBackgroundResource(R.drawable.dado_animado)
        miAnimacionDados = imgContenedor.background as AnimationDrawable


        //Listener de grupo de botones.
        btnGroup.addOnButtonCheckedListener { x, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener //salir

            var arrayResId = when (checkedId) {
                R.id.btnParImpar -> R.array.desplegableParImpar
                R.id.btnMayorMenor -> R.array.desplegableMayorMenor7
                else -> return@addOnButtonCheckedListener //salir
            }

            var adaptador = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                android.R.layout.simple_spinner_item
            )

            adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )
            desplegable.adapter = adaptador
        }

        //EVENTOS BOTON DADO:
        //si no se ha elegido ninguna opcion, da error el boton
        btnDados.setOnClickListener {
            //leer saldo actual
            val txtSaldo = findViewById<TextView>(R.id.txtSaldo)
            val saldoActual = txtSaldo.text.toString().toDouble()

            if (validarSeleccion() && validarCantidad(saldoActual) ) {

                lifecycleScope.launch {

                    val etApuesta = findViewById<EditText>(R.id.apuestaUser)
                    val apuesta = etApuesta.text.toString().toDouble()
                    val eleccion = desplegable.selectedItem.toString()

                    imgContenedor.visibility = android.view.View.VISIBLE
                    miAnimacionDados.start()
                    delay(1500)
                    miAnimacionDados.stop()
                    imgContenedor.visibility = android.view.View.GONE

                    val valorDado1 = generarNumAleatorio()
                    val valorDado2 = generarNumAleatorio()
                    val suma = valorDado1 + valorDado2

                    tvDado1.text = valorDado1.toString()
                    tvDado2.text = valorDado2.toString()

                    val saldoFinal = when (btnGroup.checkedButtonId) {
                        R.id.btnParImpar -> {
                            logicaParImpar(saldoActual, apuesta, eleccion, suma)
                        }

                        R.id.btnMayorMenor -> {
                            logicaJuego7(saldoActual, apuesta, eleccion, suma)
                        }

                        else -> saldoActual
                    }

                    imgResultado.visibility = android.view.View.VISIBLE
                    when {
                        saldoFinal <= 0 -> imgResultado.setImageResource(R.drawable.bancarrota)
                        saldoFinal > saldoActual -> imgResultado.setImageResource(R.drawable.ganar_dados)
                        else -> imgResultado.setImageResource(R.drawable.perder_dados)
                    }

                    //actualizar datos
                    txtSaldo.text = saldoFinal.toString()
                    etApuesta.text.clear()

                    //espera de la ventana
                        delay(2000) //un segundo y medio de espera
                        mostrarDialogoContinuar(saldoFinal);

                }
            }
        }


    }

    private fun validarSeleccion(): Boolean {
        val grupoBotones = findViewById<com.google.android.material.button.MaterialButtonToggleGroup>(R.id.btnGroup)

        if (grupoBotones.checkedButtonId == -1) {
            Toast.makeText(this, "Por favor, elige un modo de juego", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun validarCantidad (saldoActual : Double) : Boolean {
        val dineroApostado = findViewById<EditText>(R.id.apuestaUser) //es d ese type??
        val textoApuesta = dineroApostado.text.toString()

        //si el usuario no ha puesto ninguna cantidad, avisa
        if (textoApuesta.isEmpty()) {
            Toast.makeText(this, "Introduce una cantidad para apostar", Toast.LENGTH_SHORT).show()
            return false
        }
        val cantidadApostada = textoApuesta.toDouble()
        //si el usuario intenta sacar mas de lo que es permitido, avisa
        if (cantidadApostada > saldoActual) {
            Toast.makeText(this, "No tienes sufiicente saldo", Toast.LENGTH_SHORT).show()
            return false
        }
        //si el usuario intenta sacar una cantidad negativa
        if (cantidadApostada <= 0) {
            Toast.makeText(this, "La apuesta tiene que ser mayor que 0", Toast.LENGTH_SHORT).show()
            return false;
        }
        //si ha pasado por todas las validaciones, es una cantidad valida!
        return true;
    }

    //modo de juego: par - impar
    fun logicaParImpar (saldoActual: Double, apuesta: Double, eleccion: String, num: Int) : Double {
        //verificar si es par o impar el num comparando con la eleccion escogida en el desplegable.
        var resultadoDado = if (num % 2 == 0) "Par" else "Impar"
        var nuevoSaldo : Double
        if(eleccion == resultadoDado) {
            nuevoSaldo = saldoActual + apuesta
        } else {
            nuevoSaldo = saldoActual - apuesta
        }
        /*que se vea por debajo de las imagenes el num de los dados q han salido*/
        return nuevoSaldo
    }

    //modo de juego: mayor que 7
    fun logicaJuego7 (saldoActual: Double, apuesta: Double, eleccion: String, num: Int) : Double {
        var resultadoDado = if (num >= 7) "Mayor o Igual que 7" else "Menor que 7"
        var nuevoSaldo : Double
        if (eleccion == resultadoDado) {
            nuevoSaldo = saldoActual + apuesta
        } else {
            nuevoSaldo = saldoActual - apuesta
        }
        /*que se vea por debajo de las imagenes el num de los dados q han salido*/
        return nuevoSaldo;
    }

    fun generarNumAleatorio () : Int {
        return (1..6).random();
    }

    fun mostrarDialogoContinuar(saldoActual: Double) {
        val builder = android.app.AlertDialog.Builder(this)

        if (saldoActual <= 0) {
            builder.setTitle("BANCARROTA")
            builder.setMessage("Te has quedado sin dinero. Sal del juego!")

            builder.setPositiveButton("Salir del juego") { x, a ->
                finish()
            }

        } else {
            builder.setTitle("Jugando a los dados")
            builder.setMessage("¿Desea seguir jugando?")

            builder.setPositiveButton("Seguir jugando") { dialog, x ->
                dialog.dismiss() //simplemente se cierra el dialogo
                    // puedes limpiar dados para la siguiente ronda
                    findViewById<TextView>(R.id.dado1).text = ""
                    findViewById<TextView>(R.id.dado2).text = ""
                    //limpiar imagenes:
                    findViewById<ImageView>(R.id.imgResultado).visibility = android.view.View.GONE
                }

                builder.setNegativeButton("Salir del juego") { x, a ->
                    finish() // cierra la actividad actual
                }

                // evita que el usuario lo cierre tocando fuera de la ventana
            builder.setCancelable(false)
        }

        val dialog = builder.create()
        dialog.show()

    }
}