package com.akashi.animelistdatabase.data.repository

import com.akashi.animelistdatabase.data.interfaces.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MangaRepository {
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

    /**
     * Search for manga
     * @param query The query to search by
     * @return The response body with the search results
     */
    fun searchManga(query: String?): Call<ResponseBody> {
        return apiService.searchManga(query, 10) ?: throw NullPointerException("The API service is null")
    }

    /**
     * Get a manga by its ID
     * @param id The ID of the manga
     * @return The response body with the manga data
     */
    fun getManga(id: Int): Call<ResponseBody> {
        return apiService.getManga(id) ?: throw NullPointerException("The API service is null")
    }

    /**
     * Get the top manga from the API
     * @return The response body with the top manga data
     */
    fun getTopManga(): Call<ResponseBody> {
        return apiService.getTopManga() ?: throw NullPointerException("The API service is null")
    }
}