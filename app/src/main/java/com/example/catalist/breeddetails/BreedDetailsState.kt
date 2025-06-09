package com.example.catalist.breeddetails

import com.example.catalist.model.Breed

sealed class BreedDetailsState {
    object Loading : BreedDetailsState()
    data class Success(val breed: Breed, val imageUrl: String?) : BreedDetailsState()
    data class Error(val message: String) : BreedDetailsState()
}
