package com.akashi.animelistdatabase.data.repository

import com.akashi.animelistdatabase.data.interfaces.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnimeRepository {
    private val baseUrl = "https://api.jikan.moe/v4/"
    private var apiService: ApiService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun searchAnime(query: String?): Call<ResponseBody> {
        return apiService.searchAnime(query, 10) ?: throw NullPointerException("The API service is null")
    }

    fun getAnime(id: Int): Call<ResponseBody> {
        return apiService.getAnime(id) ?: throw NullPointerException("The API service is null")
    }

    fun getTopAnime(): Call<ResponseBody> {
        return apiService.getTopAnime() ?: throw NullPointerException("The API service is null")
    }
}