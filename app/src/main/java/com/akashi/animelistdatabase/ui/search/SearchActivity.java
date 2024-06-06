package com.akashi.animelistdatabase.ui.search;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akashi.animelistdatabase.R;
import com.akashi.animelistdatabase.data.model.AnimeTopResponse;
import com.akashi.animelistdatabase.data.model.CardItem;

import java.io.IOException;
import java.util.ArrayList;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akashi.animelistdatabase.R;
import com.akashi.animelistdatabase.data.model.AnimeSearchResponse;
import com.akashi.animelistdatabase.data.model.CardItem;
import com.akashi.animelistdatabase.data.repository.AnimeRepository;
import com.google.gson.Gson;

import okhttp3.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private AnimeRepository animeRepository;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAlpha(0f);

        animeRepository = new AnimeRepository();

        searchEditText = findViewById(R.id.search_edit_text); // replace with your EditText id

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    testApiCall(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        Button searchButton = findViewById(R.id.search_button); // replace with your Button id

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                testApiCall(query);
            }
        });

        topanime();
    }

    private void testApiCall(String query) {
        animeRepository.searchAnime(query).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        Log.d("TestApiCall", "Response body: " + json);

                        Gson gson = new Gson();
                        AnimeSearchResponse animeResponse = gson.fromJson(json, AnimeSearchResponse.class);

                        List<AnimeSearchResponse.Anime> animes = animeResponse.data;
                        List<CardItem> cardItemList = new ArrayList<>();

                        for (AnimeSearchResponse.Anime anime : animes) {
                            String title = anime.title;
                            String imageUrl = anime.images.jpg.imageUrl;
                            int malId = anime.malId;
                            cardItemList.add(new CardItem(title, imageUrl, malId));
                        }

                        cardAdapter = new CardAdapter(cardItemList, SearchActivity.this);
                        recyclerView.setAdapter(cardAdapter);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("TestApiCall", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TestApiCall", "onFailure: ", t);
            }
        });
    }

    private void topanime() {
        animeRepository.getTopAnime().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        Log.d("TestApiCall", "Response body: " + json);

                        Gson gson = new Gson();
                        AnimeTopResponse animeResponse = gson.fromJson(json, AnimeTopResponse.class);

                        List<AnimeSearchResponse.Anime> animes = animeResponse.data;
                        List<CardItem> cardItemList = new ArrayList<>();

                        for (AnimeSearchResponse.Anime anime : animes) {
                            String title = anime.title;
                            String imageUrl = anime.images.jpg.imageUrl;
                            int malId = anime.malId;
                            cardItemList.add(new CardItem(title, imageUrl, malId));
                        }

                        cardAdapter = new CardAdapter(cardItemList, SearchActivity.this);
                        recyclerView.setAdapter(cardAdapter);

                        recyclerView.animate()
                                .alpha(1f) // Final alpha value (1 = opaque, 0 = invisible)
                                .setDuration(1000) // Duration in milliseconds
                                .setListener(null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("TestApiCall", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TestApiCall", "onFailure: ", t);
            }
        });
    }
/*
    private void searchAnime(String query) {
        animeRepository.searchAnime(query).enqueue(new Callback<AnimeResponse>() {
            @Override
            public void onResponse(Call<AnimeResponse> call, Response<AnimeResponse> response) {
                Log.e("SearchActivity", "onResponseMe: " + response.body());

                if (response.isSuccessful() && response.body() != null) {

                    Log.e("SearchActivity", "onResponse: " + response.body());

                    List<AnimeResponse.Anime> animes = response.body().data;
                    List<CardItem> cardItemList = new ArrayList<>();

                    for (AnimeResponse.Anime anime : animes) {
                        String title = anime.title;
                        String imageUrl = anime.images.jpg.imageUrl;
                        cardItemList.add(new CardItem(title, imageUrl));
                    }

                    cardAdapter = new CardAdapter(cardItemList, SearchActivity.this);
                    recyclerView.setAdapter(cardAdapter);
                } else {
                    Toast.makeText(SearchActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnimeResponse> call, Throwable t) {
                Log.e("SearchActivity", "onFailure: ", t);
                Toast.makeText(SearchActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
