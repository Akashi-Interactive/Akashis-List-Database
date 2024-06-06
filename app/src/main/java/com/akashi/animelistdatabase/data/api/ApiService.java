package com.akashi.animelistdatabase.data.api;

import com.akashi.animelistdatabase.data.model.AnimeResponse;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

        @GET("anime")
        Call<ResponseBody> searchAnime(@Query("q") String query, @Query("limit") int limit);
}