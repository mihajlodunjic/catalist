import com.example.catalist.model.Breed

sealed class BreedListState {
    object Loading : BreedListState()
    data class Success(
        val breeds: List<Breed>,
        val query: String = ""
    ) : BreedListState()
    data class Error(val message: String) : BreedListState()
}
