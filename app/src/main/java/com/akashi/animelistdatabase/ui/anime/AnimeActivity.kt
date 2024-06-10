package com.akashi.animelistdatabase.ui.anime

import android.content.ContentValues
import android.os.Bundle
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
    private lateinit var animeImageView: ImageView
    private lateinit var animeDataLayout: ConstraintLayout
    private lateinit var animeTitle: TextView
    private lateinit var animeJapaneseTitle: TextView
    private lateinit var animeSynopsis: TextView
    private lateinit var animeEpisodes: TextView
    private lateinit var animeAired: TextView
    private lateinit var animeSource: TextView

    private var dbHelper: AnimeDatabaseHelper = AnimeDatabaseHelper(this)
    private var isFavorite = false
    private var imageUrl : String = ""
    private lateinit var db: android.database.sqlite.SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime)

        val id = intent.getIntExtra("malId", 40974)
        Log.w("AnimeActivity", "ID: $id")

        loadReferences()
        initializeSystem()
        initializeLayout()
        checkIfInFavorites(id)
        callAnime(id)

        db.close()
    }

    /**
     * Function that loads the references to the layout elements
     */
    private fun loadReferences(){
        favoriteButton = findViewById(R.id.FavButton)
        animeDataLayout = findViewById(R.id.anime_data)
        animeImageView = findViewById(R.id.anime_image)
        animeTitle = findViewById(R.id.EnglishTitle)
        animeJapaneseTitle = findViewById(R.id.JapaneseTitle)
        animeSynopsis = findViewById(R.id.Synopsis)
        animeEpisodes = findViewById(R.id.Episodes)
        animeAired = findViewById(R.id.Aired)
        animeSource = findViewById(R.id.Source)
    }

    /**
     * Function that initializes the system
     */
    private fun initializeSystem(){
        dbHelper = AnimeDatabaseHelper(this)
        db = dbHelper.writableDatabase
        animeRepository = AnimeRepository()
    }

    /**
     * Function that initializes the layout
     */
    private fun initializeLayout(){
        animeDataLayout.alpha = 0f
        animeImageView.alpha = 0f
        favoriteButton.alpha = 0f
    }

    /**
     * Function that checks if the anime is in the favorites
     * @param id The ID of the anime
     */
    private fun checkIfInFavorites(id: Int) {
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
            favoriteButton.setImageResource(R.drawable.bookmarked)
        } else {
            Log.d("AnimeActivity", "Anime not found in database")
            isFavorite = false
            favoriteButton.setImageResource(R.drawable.bookmark)
        }

        cursor.close()
    }

    /**
     * Function that calls the API to get the anime data
     * @param id The ID of the anime
     */
    private fun callAnime(id: Int){
        val call = animeRepository.getAnime(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val json = response.body()?.string()
                        processAnimeData(json!!)
                        showAnimeData()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("AnimeActivity", "Response is not successful or body is null")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ApiCall", "onFailure: ", t)
            }
        } )
    }

    /**
     * Function that processes the anime data
     * @param json The JSON string with the anime data
     */
    private fun processAnimeData(json: String){
        val gson = Gson()
        val animeResponse = gson.fromJson(json, AnimeResponse::class.java)
        Log.d("AnimeActivity", animeResponse.toString())

        imageUrl = animeResponse.data?.images?.jpg?.largeImageUrl.toString()

        Glide.with(this@AnimeActivity)
            .load(imageUrl)
            .into(animeImageView)

        animeTitle.text = animeResponse.data?.title
        animeJapaneseTitle.text = animeResponse.data?.titleJapanese
        animeSynopsis.text = animeResponse.data?.synopsis
        animeEpisodes.text = animeResponse.data?.episodes.toString()
        animeAired.text = animeResponse.data?.aired?.string
        animeSource.text = animeResponse.data?.source
    }

    /**
     * Function that shows the anime data with an animation
     */
    private fun showAnimeData(){
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
    }

    /**
     * Function that handles the click on the favorite button
     * It adds or removes the anime from the favorites
     * depending on its current state
     * @param view The view that was clicked
     */
    fun onFavoriteClick(view: android.view.View) {
        val dbHelper = AnimeDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        // If the anime is already a favorite, remove it
        if (isFavorite) {
            val selection = "${AnimeEntry.COLUMN_MAL_ID} = ?" // WHERE clause
            val selectionArgs = arrayOf(intent.getIntExtra("malId", 40974).toString()) // WHERE arguments
            db.delete(AnimeEntry.TABLE_NAME, selection, selectionArgs) // Delete the anime from the database
            favoriteButton.setImageResource(R.drawable.bookmark) // Change the icon
            isFavorite = false // Change the state of the anime

        // If the anime is not a favorite, add it
        } else {
            val values = ContentValues().apply { // Create a new row
                put(AnimeEntry.COLUMN_MAL_ID, intent.getIntExtra("malId", 40974)) // Add the malId
                put(AnimeEntry.COLUMN_TITLE, findViewById<TextView>(R.id.EnglishTitle).text.toString()) // Add the title
                put(AnimeEntry.COLUMN_IMAGE_URL, imageUrl) // Add the image URL
            }
            db.insert(AnimeEntry.TABLE_NAME, null, values) // Insert the new row
            favoriteButton.setImageResource(R.drawable.bookmarked) // Change the icon
            isFavorite = true // Change the state of the anime
        }

        db.close()
    }
}