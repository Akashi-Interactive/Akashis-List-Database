package com.akashi.animelistdatabase.data.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface ApiService {

        /**
         * Search anime by query
         * @param query query to search by
         * @param limit number of max results per page
         * @return ResponseBody with the search results
         */
        @GET("anime")
        Call<ResponseBody> searchAnime(@Query("q") String query, @Query("limit") int limit);

        /**
         * Get anime by id
         * @param id id of the anime
         * @return ResponseBody with the anime
         */
        @GET("anime/{id}")
        Call<ResponseBody> getAnime(@Path("id") int id);

        /**
         * Get full anime data by id
         * @param id id of the anime
         * @return ResponseBody with the full anime data
         */
        @GET("anime/{id}/full")
        Call<ResponseBody> getFullAnime(@Path("id") int id);

        /**
         * Get top animes
         * @return ResponseBody with the top animes
         */
        @GET("top/anime")
        Call<ResponseBody> getTopAnime();

        /**
         * Get top airing animes
         * @return ResponseBody with the top airing animes
         */
        @GET("top/seasons/now")
        Call<ResponseBody> getTopAiringAnime();
}