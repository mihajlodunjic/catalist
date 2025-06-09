package com.example.catalist.network

import com.example.catalist.model.Breed
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


object ApiClient {

    private const val BASE_URL = "https://api.thecatapi.com/v1/"

    val catApiService: CatApiService by lazy {
        val contentType = "application/json".toMediaType()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", "live_LiAMfmFAU99Y9KVmzK5a2uWP2lxDuDtd1grkLB10CYjxGQ3qDIPoib3i3aV6aKBP")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .client(client)
            .build()

        retrofit.create(CatApiService::class.java)
    }
}
