package com.example.catalist.breeddetails

import com.example.catalist.model.Breed

sealed class BreedDetailsIntent {
    data class LoadImage(val breed: Breed) : BreedDetailsIntent()
}
