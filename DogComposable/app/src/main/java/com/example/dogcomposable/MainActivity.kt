package com.example.dogcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dogcomposable.composables.NavegationScreen
import com.example.dogcomposable.model.RetrofitApi
import com.example.dogcomposable.repository.DogRepository
import com.example.dogcomposable.ui.theme.DogComposableTheme
import com.example.dogcomposable.viewmodel.BreedsViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogComposableTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val retrofitApi = RetrofitApi()
                    val apiService = retrofitApi.apiService
                    val repository = DogRepository(apiService)
                    @Suppress("ViewModelConstructorInComposable")
                    val viewModel = BreedsViewModel(repository)

                    NavegationScreen(viewModel = viewModel)
                }
            }
        }
    }
}