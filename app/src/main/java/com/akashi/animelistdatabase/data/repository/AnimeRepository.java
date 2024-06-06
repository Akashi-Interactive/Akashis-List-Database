package com.akashi.animelistdatabase.data.repository;

import android.util.Log;

import com.akashi.animelistdatabase.data.api.ApiService;
import com.akashi.animelistdatabase.data.model.AnimeSearchResponse;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeRepository {
    private static final String BASE_URL = "https://api.jikan.moe/v4/";
    private final ApiService apiService;

    public AnimeRepository() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Call<ResponseBody> searchAnime(String query) {
        return apiService.searchAnime(query, 10);
    }

    public Call<ResponseBody> getAnime(int id) {
        return apiService.getAnime(id);
    }

    public Call<ResponseBody> getTopAnime() {
        return apiService.getTopAnime();
    }
}
