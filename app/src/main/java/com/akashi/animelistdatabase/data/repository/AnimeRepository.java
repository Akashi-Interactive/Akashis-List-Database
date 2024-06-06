package com.akashi.animelistdatabase.data.repository;

import com.akashi.animelistdatabase.data.api.ApiService;
import com.akashi.animelistdatabase.data.model.AnimeResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeRepository {
    private static final String BASE_URL = "https://api.jikan.moe/v4/";
    private final ApiService apiService;

    public AnimeRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Call<ResponseBody> searchAnime(String query) {
        return apiService.searchAnime(query, 5);
    }
}
