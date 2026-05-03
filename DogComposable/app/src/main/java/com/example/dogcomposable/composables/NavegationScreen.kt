package com.example.dogcomposable.composables

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.dogcomposable.viewmodel.BreedsViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavegationScreen(viewModel: BreedsViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "lista_razas"
    ) {
        composable("lista_razas") {
            ListScreen(navController, viewModel)
        }

        //ruta para la pantalla de subrazas
        composable(
            route = "subrazas/{breed}",
            //recibe el parametro de raza!
            arguments = listOf(navArgument("breed") {
                type = NavType.StringType
            })
        ) { backStackEntry -> //backStackEntry = contiene la informacion de la navegacion actual
            val breed = backStackEntry.arguments?.getString("breed") ?: "" //si es null, cadena vacia.
            SubBreedScreen(breed, navController, viewModel)
        }

        //ruta para detalle de fotos
        composable(
            route = "detalle/{breed}?subBreed={subBreed}", //indicamos que breed es obligatorio y subBreed opcional
            arguments = listOf(
                navArgument("breed") { type = NavType.StringType },
                navArgument("subBreed") { 
                    type = NavType.StringType
                    nullable = true //puede ser null, porque puede no tener subraza
                    defaultValue = null //si no hay, es null!!
                }
            )
        ) { backStackEntry -> //extraer valores...
            val breed = backStackEntry.arguments?.getString("breed") ?: ""
            val subBreed = backStackEntry.arguments?.getString("subBreed")
            DetailScreen(breed, subBreed, navController, viewModel)
        }
    }
}
