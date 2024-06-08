package com.akashi.animelistdatabase.ui.anime

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.akashi.animelistdatabase.R
import com.akashi.animelistdatabase.data.model.reponses.AnimeResponse
import com.akashi.animelistdatabase.data.repository.AnimeRepository
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AnimeActivity : AppCompatActivity() {
    private lateinit var animeRepository: AnimeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anime_activity)

        val animeDataLayout = findViewById<ConstraintLayout>(R.id.anime_data)
        val animeImageView = findViewById<ImageView>(R.id.anime_image)

        animeDataLayout.alpha = 0f
        animeImageView.alpha = 0f

        val animeEnglishTitleTextView = findViewById<TextView>(R.id.EnglishTitle)
        val animeJapaneseTitleTextView = findViewById<TextView>(R.id.JapaneseTitle)
        val animeSynopsisTextView = findViewById<TextView>(R.id.Synopsis)
        val animeEpisodesTextView = findViewById<TextView>(R.id.Episodes)
        val animeAiredTextView = findViewById<TextView>(R.id.Aired)
        val animeSourceTextView = findViewById<TextView>(R.id.Source)

        animeRepository = AnimeRepository()

        val id = intent.getIntExtra("malId", 40974)
        Log.w("AnimeActivity", "ID: $id")
        val call = animeRepository.getAnime(id)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val json = response.body()?.string()
                        val gson = Gson()
                        val animeResponse = gson.fromJson(json, AnimeResponse::class.java)
                        Log.d("AnimeActivity", animeResponse.toString())
                        val imageUrl = animeResponse.data?.images?.jpg?.largeImageUrl

                        Glide.with(this@AnimeActivity)
                            .load(imageUrl)
                            .into(animeImageView)
                        animeEnglishTitleTextView.text = animeResponse.data?.title
                        animeJapaneseTitleTextView.text = animeResponse.data?.titleJapanese
                        animeSynopsisTextView.text = animeResponse.data?.synopsis
                        animeEpisodesTextView.text = animeResponse.data?.episodes.toString()
                        animeAiredTextView.text = animeResponse.data?.aired?.string
                        animeSourceTextView.text = animeResponse.data?.source

                        animeDataLayout.animate()
                            .alpha(1f)
                            .setDuration(1000)
                            .setListener(null)

                        animeImageView.animate()
                            .alpha(1f)
                            .setDuration(1000)
                            .setListener(null)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("AnimeActivity", "Response is not successful or body is null")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("AnimeActivity", "Error: " + t.message)
            }
        })
    }
}