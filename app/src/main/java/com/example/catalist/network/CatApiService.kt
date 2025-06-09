package com.example.catalist.network

import com.example.catalist.model.Breed
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface CatApiService {

    @GET("breeds")
    suspend fun getAllBreeds(): List<Breed>

    @GET("breeds/search")
    suspend fun searchBreeds(@Query("q") query: String): List<Breed>

    @GET("images/search")
    suspend fun getImagesForBreed(@Query("breed_ids") breedId: String): List<CatImage>

    @Serializable
    data class CatImage(
        val id: String,
        val url: String
    )

}
