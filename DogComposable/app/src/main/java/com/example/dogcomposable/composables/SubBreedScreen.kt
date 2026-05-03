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

@OptIn(ExperimentalMaterial3Api::class) //evitar error de TopAppBar
@Composable
fun SubBreedScreen(breedName: String, navController: NavController, viewModel: BreedsViewModel) {
    val breeds by viewModel.breeds.collectAsState()
    val breedItem = breeds.find { it.breed == breedName }

    //tener una barra de arriba con el nombre de la raza
    Scaffold(
        topBar = { TopAppBar(title = { Text("Subrazas de ${breedName.replaceFirstChar { it.uppercase() }}") }) }
    ) { paddingValues ->
        val subBreeds = breedItem?.subBreeds ?: emptyList()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(subBreeds) { subBreed ->
                Text(
                    text = subBreed.replaceFirstChar { it.uppercase() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            viewModel.loadPhotos(breedName, subBreed)
                            navController.navigate("detalle/${breedName}?subBreed=${subBreed}")
                        }
                )
            }
        }
    }
}
