package com.example.dogcomposable.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dogcomposable.viewmodel.BreedsViewModel

@OptIn(ExperimentalMaterial3Api::class) //para evitar error en TopAppBar.
@Composable
fun ListScreen(navController: NavController, viewModel: BreedsViewModel) {
    val breeds by viewModel.breeds.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Razas de perros") }) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(breeds) { breedItem ->
                Text(
                    text = breedItem.dogName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            if (breedItem.subBreeds.isNotEmpty()) {
                                //si tiene subrazas..
                                navController.navigate("subrazas/${breedItem.breed}")
                            } else {
                                // cargamos fotos aqui
                                viewModel.loadPhotos(breedItem.breed, null)
                                navController.navigate("detalle/${breedItem.breed}")
                            }
                        }
                )
            }
        }
    }
}
