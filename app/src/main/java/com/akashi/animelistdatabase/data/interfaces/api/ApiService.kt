package com.akashi.animelistdatabase.data.interfaces.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface for the API service
 */
interface ApiService {

    /**
     * Search for anime
     * @param query The query to search by
     * @param limit The number of results to return per page
     * @return The response body with the search results
     */
    @GET("anime")
    fun searchAnime(@Query("q") query: String?, @Query("limit") limit: Int): Call<ResponseBody>

    /**
     * Get an anime by its ID
     * @param id The ID of the anime
     * @return The response body with the anime data
     */
    @GET("anime/{id}")
    fun getAnime(@Path("id") id: Int): Call<ResponseBody>

    /**
     * Get the full anime data by its ID
     * @param id The ID of the anime
     * @return The response body with the full anime data
     */
    @GET("anime/{id}/full")
    fun getFullAnime(@Path("id") id: Int): Call<ResponseBody>

    /**
     * Get the top anime
     * @return The response body with the top anime
     */
    @GET("top/anime")
    fun getTopAnime(): Call<ResponseBody>

    /**
     * Get the top airing anime
     * @return The response body with the top airing anime
     */
    @GET("top/seasons/now")
    fun getTopAiringAnime(): Call<ResponseBody>

    /**
     * Get the top upcoming anime
     * @return The response body with the top upcoming anime
     */
    @GET("top/seasons/upcoming")
    fun getTopUpcomingAnime(): Call<ResponseBody>

    /**
     * Get a character by its ID
     * @return The response body with character data
     */
    @GET("characters/{id}")
    fun getCharacter(@Path("id") id: Int): Call<ResponseBody>

    /**
     * Get a character by its ID
     * @return The response body with the full character data
     */
    @GET("characters/{id}/full")
    fun getFullCharacter(@Path("id") id: Int): Call<ResponseBody>

    /**
     * Search for a character
     * @param query The query to search by
     * @param limit The number of results to return per page
     * @return The response body with the search results
     */
    @GET("characters")
    fun searchCharacter(@Query("q") query: String?, @Query("limit") limit: Int): Call<ResponseBody>

    /**
     * Get the top characters
     * @return The response body with the top characters
     */
    @GET("top/characters")
    fun getTopCharacters(): Call<ResponseBody>

    /**
     * Get a manga by its ID
     * @param id The ID of the manga
     * @return The response body with manga data
     */
     @GET("manga/{id}")
     fun getManga(@Path("id") id: Int): Call<ResponseBody>

    /**
     * Get the full manga data by its ID
     * @param id The ID of the manga
     * @return The response body with the full manga data
     */
    @GET("manga/{id}/full")
    fun getFullManga(@Path("id") id: Int): Call<ResponseBody>

    /**
     * Get the top manga
     * @return The response body with the top manga
     */
    @GET("top/manga")
    fun getTopManga(): Call<ResponseBody>

    /**
     * Search for manga
     * @param query The query to search by
     * @param limit The number of results to return per page
     * @return The response body with the search results
     */
    @GET("manga")
    fun searchManga(@Query("q") query: String?, @Query("limit") limit: Int): Call<ResponseBody>
}