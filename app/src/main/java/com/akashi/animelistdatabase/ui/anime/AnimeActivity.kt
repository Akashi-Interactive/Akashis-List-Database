package com.akashi.animelistdatabase.ui.anime

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.akashi.animelistdatabase.R
import com.akashi.animelistdatabase.data.model.reponses.AnimeResponse
import com.akashi.animelistdatabase.data.repository.AnimeRepository
import com.akashi.animelistdatabase.database.entry.AnimeEntry
import com.akashi.animelistdatabase.database.helper.AnimeDatabaseHelper
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AnimeActivity : AppCompatActivity() {
    private lateinit var animeRepository: AnimeRepository
    private lateinit var favoriteButton: ImageButton
    private  var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime)

        favoriteButton = findViewById(R.id.FavButton)

        val dbHelper = AnimeDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val animeDataLayout = findViewById<ConstraintLayout>(R.id.anime_data)
        val animeImageView = findViewById<ImageView>(R.id.anime_image)

        animeDataLayout.alpha = 0f
        animeImageView.alpha = 0f
        favoriteButton.alpha = 0f

        val animeEnglishTitleTextView = findViewById<TextView>(R.id.EnglishTitle)
        val animeJapaneseTitleTextView = findViewById<TextView>(R.id.JapaneseTitle)
        val animeSynopsisTextView = findViewById<TextView>(R.id.Synopsis)
        val animeEpisodesTextView = findViewById<TextView>(R.id.Episodes)
        val animeAiredTextView = findViewById<TextView>(R.id.Aired)
        val animeSourceTextView = findViewById<TextView>(R.id.Source)

        animeRepository = AnimeRepository()

        val id = intent.getIntExtra("malId", 40974)
        Log.w("AnimeActivity", "ID: $id")

        val projection = arrayOf(AnimeEntry.COLUMN_MAL_ID, AnimeEntry.COLUMN_TITLE)
        val selection = "${AnimeEntry.COLUMN_MAL_ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            AnimeEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val malId = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeEntry.COLUMN_MAL_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(AnimeEntry.COLUMN_TITLE))
            Log.d("AnimeActivity", "Anime found in database: ID = $malId, Title = $title")
            isFavorite = true
            favoriteButton.setImageResource(R.drawable.favorite)
        } else {
            Log.d("AnimeActivity", "Anime not found in database")
            isFavorite = false
            favoriteButton.setImageResource(R.drawable.not_favorite)
        }

        cursor.close()

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

                        favoriteButton.animate()
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

        db.close()
    }

    fun onFavoriteClick(view: android.view.View) {
        val dbHelper = AnimeDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        if (isFavorite) {
            val selection = "${AnimeEntry.COLUMN_MAL_ID} = ?"
            val selectionArgs = arrayOf(intent.getIntExtra("malId", 40974).toString())
            db.delete(AnimeEntry.TABLE_NAME, selection, selectionArgs)
            favoriteButton.setImageResource(R.drawable.not_favorite)
            isFavorite = false
        } else {
            val values = ContentValues().apply {
                put(AnimeEntry.COLUMN_MAL_ID, intent.getIntExtra("malId", 40974))
                put(AnimeEntry.COLUMN_TITLE, findViewById<TextView>(R.id.EnglishTitle).text.toString())
            }
            db.insert(AnimeEntry.TABLE_NAME, null, values)
            favoriteButton.setImageResource(R.drawable.favorite)
            isFavorite = true
        }

        db.close()
    }
}