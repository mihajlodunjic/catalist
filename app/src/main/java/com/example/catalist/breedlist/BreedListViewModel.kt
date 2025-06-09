package com.example.catalist.breedlist

import BreedListState
import androidx.lifecycle.SavedStateHandle
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
class BreedListViewModel @Inject constructor(
    private val repository: CatRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<BreedListState>(BreedListState.Loading)
    val state: StateFlow<BreedListState> = _state.asStateFlow()

    private var allBreeds: List<Breed> = emptyList()

    companion object {
        private const val KEY_QUERY = "search_query"
    }

    fun handleIntent(intent: BreedListIntent) {
        when (intent) {
            is BreedListIntent.LoadBreeds -> loadBreeds()
            is BreedListIntent.SearchBreeds -> search(intent.query)
        }
    }

    private fun loadBreeds() {
        viewModelScope.launch {
            _state.value = BreedListState.Loading
            when (val result = repository.getAllBreeds()) {
                is Result.Success -> {
                    allBreeds = result.data
                    val currentQuery = savedStateHandle.get<String>(KEY_QUERY).orEmpty()
                    val filtered = filterList(currentQuery)
                    _state.value = BreedListState.Success(filtered, currentQuery)
                }
                is Result.Error -> {
                    _state.value = BreedListState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    private fun search(query: String) {
        savedStateHandle[KEY_QUERY] = query
        val filtered = filterList(query)
        _state.value = BreedListState.Success(filtered, query)
    }

    private fun filterList(query: String): List<Breed> {
        return if (query.isBlank()) allBreeds
        else allBreeds.filter { it.name.contains(query, ignoreCase = true) }
    }
}
