package com.akashi.animelistdatabase.data.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface ApiService {

        @GET("anime")
        Call<ResponseBody> searchAnime(@Query("q") String query, @Query("limit") int limit);

        @GET("anime/{id}")
        Call<ResponseBody> getAnime(@Path("id") int id);

        @GET("top/anime")
        Call<ResponseBody> getTopAnime();
}