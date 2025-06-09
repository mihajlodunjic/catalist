package com.example.catalist.breeddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.model.Breed
import com.example.catalist.repository.CatRepository
import com.example.catalist.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    private val _state = MutableStateFlow<BreedDetailsState>(BreedDetailsState.Loading)
    val state: StateFlow<BreedDetailsState> = _state.asStateFlow()

    fun handleIntent(intent: BreedDetailsIntent) {
        when (intent) {
            is BreedDetailsIntent.LoadImage -> loadBreedImage(intent.breed)
        }
    }

    private fun loadBreedImage(breed: Breed) {
        viewModelScope.launch {
            _state.value = BreedDetailsState.Loading
            when (val result = repository.getImageForBreed(breed.id)) {
                is Result.Success -> {
                    val imageUrl = result.data
                    _state.value = BreedDetailsState.Success(breed, imageUrl)
                }
                is Result.Error -> {
                    _state.value = BreedDetailsState.Error(result.message)
                }
                else -> {}
            }
        }
    }
}
