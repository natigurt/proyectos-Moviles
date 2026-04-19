package com.example.calculadora_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculadora_compose.model.Datos
import com.example.calculadora_compose.ui.theme.FondoBase
import com.example.calculadora_compose.ui.theme.Rosa
import com.example.calculadora_compose.ui.theme.RosaPalo
import com.example.calculadora_compose.ui.theme.Lila
import com.example.calculadora_compose.viewmodel.CalculadoraViewModel

@Composable
fun baseCalculadora(
    viewModel: CalculadoraViewModel = viewModel()
) {
    // remember -> almacenar valores a lo largo del ciclo de vida
    //mutableSateOf -> como un observable
    /*
    var userNum1 by remember { mutableStateOf("") }
    var userNum2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("0.0") } // Variable para el resultado
*/
    val misDatos by viewModel.misDatosObservables.observeAsState(initial = Datos("", "", "0.0"))


    Box(modifier = Modifier.fillMaxSize().background(FondoBase)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //contiene los numeros
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Número 1: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Rosa)
                    OutlinedTextField(
                        value = misDatos.primerNum,
                        onValueChange = { viewModel.actualizarNums(it, true) }, // it es el nuevo texto
                        label = { Text("Introduce un número") },
                        modifier = Modifier.weight(1f) // Cambiado a 1f para que sea proporcional
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Número 2: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Rosa)
                    OutlinedTextField(
                        value = misDatos.segundoNum,
                        onValueChange = { viewModel.actualizarNums(it, false) },
                        label = { Text("Introduce otro número") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            //botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //por cada boton meter un spacer
                Button (
                    onClick = { viewModel.operar("+") },
                    colors = ButtonDefaults.buttonColors(containerColor = RosaPalo)
                ) {
                    Text("+", fontSize = 20.sp)
                }

                Spacer (modifier = Modifier.width(5.dp))

                Button(
                    onClick = { viewModel.operar("-") },
                    colors = ButtonDefaults.buttonColors(containerColor = Lila)
                ) {
                    Text("-", fontSize = 20.sp)
                }

                Spacer (modifier = Modifier.width(5.dp))

                Button(
                    onClick = { viewModel.operar("/") },
                    colors = ButtonDefaults.buttonColors(containerColor = RosaPalo)
                ) {
                    Text("/", fontSize = 20.sp)
                }

                Spacer (modifier = Modifier.width(5.dp))

                Button(
                    onClick = { viewModel.operar("*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Lila)
                ) {
                    Text("*", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            //ultimo un column con un text
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = misDatos.resultado,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}