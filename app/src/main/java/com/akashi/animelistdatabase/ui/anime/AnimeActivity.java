package com.akashi.animelistdatabase.ui.anime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akashi.animelistdatabase.R;
import com.akashi.animelistdatabase.data.model.AnimeResponse;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akashi.animelistdatabase.R;
import com.akashi.animelistdatabase.data.model.AnimeSearchResponse;
import com.akashi.animelistdatabase.data.model.CardItem;
import com.akashi.animelistdatabase.data.repository.AnimeRepository;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import okhttp3.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeActivity extends AppCompatActivity {
    private AnimeRepository animeRepository;

    private ImageView animeImageView;
    private TextView animeEnglishTitleTextView;
    private TextView animeJapaneseTitleTextView;
    private TextView animeSynopsisTextView;
    private TextView animeEpisodesTextView;
    private TextView animeAiredTextView;
    private TextView animeSourceTextView;

    ConstraintLayout animeDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_activity);

        animeDataLayout = findViewById(R.id.anime_data);
        animeImageView = findViewById(R.id.anime_image);

        animeDataLayout.setAlpha(0f);
        animeImageView.setAlpha(0f);


        animeEnglishTitleTextView = findViewById(R.id.EnglishTitle);
        animeJapaneseTitleTextView = findViewById(R.id.JapaneseTitle);
        animeSynopsisTextView = findViewById(R.id.Synopsis);
        animeEpisodesTextView = findViewById(R.id.Episodes);
        animeAiredTextView = findViewById(R.id.Aired);
        animeSourceTextView = findViewById(R.id.Source);

        animeRepository = new AnimeRepository();

        int id = getIntent().getIntExtra("malId", 40974);
        Log.w("AnimeActivity", "ID: " + id);
        Call<ResponseBody> call = animeRepository.getAnime(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        AnimeResponse animeResponse = gson.fromJson(json, AnimeResponse.class);
                        Log.d("AnimeActivity", animeResponse.toString());
                        String imageUrl = animeResponse.data.images.jpg.largeImageUrl;

                        Glide.with(AnimeActivity.this)
                                .load(imageUrl)
                                .into(animeImageView);
                        animeEnglishTitleTextView.setText(animeResponse.data.title);
                        animeJapaneseTitleTextView.setText(animeResponse.data.titleJapanese);
                        animeSynopsisTextView.setText(animeResponse.data.synopsis);
                        animeEpisodesTextView.setText(String.valueOf(animeResponse.data.episodes));
                        animeAiredTextView.setText(String.valueOf(animeResponse.data.aired.string));
                        animeSourceTextView.setText(animeResponse.data.source);

                        animeDataLayout.animate()
                                .alpha(1f)
                                .setDuration(1000)
                                .setListener(null);

                        animeImageView.animate()
                                .alpha(1f)
                                .setDuration(1000)
                                .setListener(null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("AnimeActivity", "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("AnimeActivity", "Error: " + t.getMessage());
            }
        });
    }
}
