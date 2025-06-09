package com.example.catalist.breedlist

sealed class BreedListIntent {
    object LoadBreeds : BreedListIntent()
    data class SearchBreeds(val query: String) : BreedListIntent()
}
