package com.example.catalist.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catalist.breedlist.BreedListIntent
import com.example.catalist.breedlist.BreedListViewModel
import com.example.catalist.model.Breed
import com.example.catalist.repository.CatRepository
import com.example.catalist.util.Result
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun BreedListScreen(
    navController: androidx.navigation.NavController,
    viewModel: BreedListViewModel = androidx.hilt.navigation.compose.hiltViewModel()
)
 {
    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.handleIntent(BreedListIntent.LoadBreeds)
    }

    when (state) {
        is BreedListState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is BreedListState.Error -> {
            val message = (state as BreedListState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text(text = "Greška: $message")
            }
        }

        is BreedListState.Success -> {
            val state by viewModel.state.collectAsState()

            val breeds = (state as? BreedListState.Success)?.breeds.orEmpty()
            val query = (state as? BreedListState.Success)?.query.orEmpty()






            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {

                OutlinedTextField(
                    value = query,
                    onValueChange = {
                        viewModel.handleIntent(BreedListIntent.SearchBreeds(it))
                    },
                    label = { Text("Pretraži rase") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                LazyColumn {
                    items(breeds) { breed ->
                        BreedListItem(breed) {
                            val breedJson = Json.encodeToString(breed)
                            navController.navigate("details/${java.net.URLEncoder.encode(breedJson, "UTF-8")}")
                        }
                        Divider()
                    }
                }

            }
        }

        else -> {}
    }
}
@Composable
fun BreedListItem(breed: Breed, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Text(text = breed.name, style = MaterialTheme.typography.titleMedium)
        if (!breed.altNames.isNullOrEmpty()) {
            Text(text = "Alt imena: ${breed.altNames}", style = MaterialTheme.typography.bodySmall)
        }
        Text(text = breed.description.take(250), style = MaterialTheme.typography.bodyMedium)
    }
}
