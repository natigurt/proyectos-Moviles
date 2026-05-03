package com.example.dogcomposable.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import coil.compose.AsyncImage
import com.example.dogcomposable.viewmodel.BreedsViewModel

@OptIn(ExperimentalMaterial3Api::class) //evitar error de TopAppBar
@Composable
fun DetailScreen(
    breed: String,
    subBreed: String?,
    navController: NavController,
    viewModel: BreedsViewModel
) {
    val photos by viewModel.photos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Fotos de $breed${subBreed?.let { " - $it" } ?: ""}")
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(photos) { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Foto de $breed",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(4.dp)
                )
            }
        }
    }
}
