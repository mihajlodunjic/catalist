package com.example.catalist.repository

import com.example.catalist.model.Breed
import com.example.catalist.network.ApiClient
import com.example.catalist.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class CatRepository @Inject constructor(){

    private val api = ApiClient.catApiService

    suspend fun getAllBreeds(): Result<List<Breed>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                api.getAllBreeds()
            }
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error("Greška u mreži: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Neočekivana greška: ${e.message}")
        }
    }

    suspend fun searchBreeds(query: String): Result<List<Breed>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                api.searchBreeds(query)
            }
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error("Greška u mreži: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Neočekivana greška: ${e.message}")
        }
    }

    suspend fun getImageForBreed(breedId: String): Result<String?> {
        return try {
            val response = withContext(Dispatchers.IO) {
                api.getImagesForBreed(breedId)
            }
            val imageUrl = response.firstOrNull()?.url
            Result.Success(imageUrl)
        } catch (e: IOException) {
            Result.Error("Greška u mreži: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Neočekivana greška: ${e.message}")
        }
    }

}
