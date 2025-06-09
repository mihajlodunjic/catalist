package com.example.catalist.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.catalist.breeddetails.BreedDetailsIntent
import com.example.catalist.breeddetails.BreedDetailsState
import com.example.catalist.breeddetails.BreedDetailsViewModel
import com.example.catalist.model.Breed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BreedDetailsScreen(
    breed: Breed,
    navController: androidx.navigation.NavController,
    viewModel: BreedDetailsViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(BreedDetailsIntent.LoadImage(breed))
    }

    when (state) {
        is BreedDetailsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is BreedDetailsState.Error -> {
            val message = (state as BreedDetailsState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Greška: $message")
            }
        }

        is BreedDetailsState.Success -> {
            val data = state as BreedDetailsState.Success
            val context = LocalContext.current

            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(text = data.breed.name) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Nazad"
                                )
                            }
                        }
                    )
                }
            ){padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        data.imageUrl?.let { imageUrl ->
                            Image(
                                painter = rememberAsyncImagePainter(imageUrl),
                                contentDescription = "Slika rase",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = data.breed.name, style = MaterialTheme.typography.headlineSmall)

                        Text(
                            text = data.breed.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        // Zemlja porekla
                        data.breed.origin?.let {
                            Text("Zemlja porekla: $it", fontWeight = FontWeight.Medium)
                        }

                        // Životni vek
                        data.breed.life_span?.let {
                            Text("Životni vek: $it godina", fontWeight = FontWeight.Medium)
                        }

                        // Težina
                        data.breed.weight?.metric?.let {
                            Text("Prosečna težina: $it kg", fontWeight = FontWeight.Medium)
                        }

                        // Temperament
                        Text("Temperament:", fontWeight = FontWeight.Medium)

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val traits = data.breed.temperament.split(",").map { it.trim() }
                            traits.forEach { trait ->
                                AssistChip(
                                    onClick = { },
                                    label = { Text(trait) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Osobine (primer 5 karakteristika)
                        Text("Osobine:")
                        BreedTrait("Inteligencija", data.breed.intelligence)
                        BreedTrait("Adaptabilnost", data.breed.adaptability)
                        BreedTrait("Energija", data.breed.energyLevel)
                        BreedTrait("Vocalizacija", data.breed.vocalisation)
                        BreedTrait("Prijateljstvo prema psima", data.breed.dogFriendly)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Retka rasa
                        if (data.breed.rare == 1) {
                            Text("Ova rasa je retka.", fontWeight = FontWeight.Bold)
                        }

                        // Wikipedia dugme
                        data.breed.wikipediaUrl?.let { url ->
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text("Otvori na Wikipediji")
                            }
                        }
                    }


                }
            }


        }
    }
}

@Composable
fun BreedTrait(label: String, value: Int?) {
    if (value != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label)
            Text(value.toString())
        }
    }
}
