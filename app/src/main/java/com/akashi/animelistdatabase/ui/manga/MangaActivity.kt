package com.akashi.animelistdatabase.ui.manga

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.akashi.animelistdatabase.R
import com.akashi.animelistdatabase.data.model.reponses.MangaResponse
import com.akashi.animelistdatabase.data.repository.MangaRepository
import com.akashi.animelistdatabase.database.entry.MangaEntry
import com.akashi.animelistdatabase.database.helper.MangaDatabaseHelper
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MangaActivity : AppCompatActivity() {
    private lateinit var mangaRepository: MangaRepository
    private lateinit var favoriteButton: ImageButton
    private lateinit var mangaImageView: ImageView
    private lateinit var mangaDataLayout: ConstraintLayout
    private lateinit var mangaTitle: TextView
    private lateinit var mangaJapaneseTitle: TextView
    private lateinit var mangaSynopsis: TextView
    private lateinit var mangaChapters: TextView
    private lateinit var mangaStatus: TextView
    private lateinit var mangaVolumes: TextView

    private var dbHelper: MangaDatabaseHelper = MangaDatabaseHelper(this)
    private var isFavorite = false
    private var imageUrl: String = ""
    private lateinit var db: android.database.sqlite.SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga)

        val id = intent.getIntExtra("malId", 40974)
        Log.w("MangaActivity", "ID: $id")

        loadReferences()
        initializeSystem()
        initializeLayout()
        checkIfInFavorites(id)
        callManga(id)

        db.close()
    }

    /**
     * Function that loads the references to the layout elements
     */
    private fun loadReferences() {
        favoriteButton = findViewById(R.id.FavButton)
        mangaDataLayout = findViewById(R.id.manga_data)
        mangaImageView = findViewById(R.id.manga_image)
        mangaTitle = findViewById(R.id.EnglishTitle)
        mangaJapaneseTitle = findViewById(R.id.JapaneseTitle)
        mangaSynopsis = findViewById(R.id.Synopsis)
        mangaChapters = findViewById(R.id.Chapters)
        mangaStatus = findViewById(R.id.Status)
        mangaVolumes = findViewById(R.id.Volumes)
    }

    /**
     * Function that initializes the system
     */
    private fun initializeSystem() {
        dbHelper = MangaDatabaseHelper(this)
        db = dbHelper.writableDatabase
        mangaRepository = MangaRepository()
    }

    /**
     * Function that initializes the layout
     */
    private fun initializeLayout() {
        mangaDataLayout.alpha = 0f
        mangaImageView.alpha = 0f
        favoriteButton.alpha = 0f
    }

    /**
     * Function that checks if the manga is in the favorites
     * @param id The ID of the anime
     */
    private fun checkIfInFavorites(id: Int) {
        val projection = arrayOf(MangaEntry.COLUMN_MAL_ID, MangaEntry.COLUMN_TITLE)
        val selection = "${MangaEntry.COLUMN_MAL_ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            MangaEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val malId = cursor.getInt(cursor.getColumnIndexOrThrow(MangaEntry.COLUMN_MAL_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(MangaEntry.COLUMN_TITLE))
            Log.d("MangaActivity", "Manga found in database: ID = $malId, Title = $title")
            isFavorite = true
            favoriteButton.setImageResource(R.drawable.bookmarked)
        } else {
            Log.d("MangaActivity", "Manga not found in database")
            isFavorite = false
            favoriteButton.setImageResource(R.drawable.bookmark)
        }

        cursor.close()
    }

    /**
     * Function that calls the API to get the manga data
     * @param id The ID of the manga
     */
    private fun callManga(id: Int) {
        val call = mangaRepository.getManga(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val json = response.body()?.string()
                        processMangaData(json!!)
                        showMangaData()
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
        })
    }

    /**
     * Function that processes the manga data
     * @param json The JSON string with the manga data
     */
    private fun processMangaData(json: String){
        val gson = Gson()
        val mangaResponse = gson.fromJson(json, MangaResponse::class.java)
        Log.d("MangaActivity", mangaResponse.toString())

        imageUrl = mangaResponse.data?.images?.jpg?.largeImageUrl.toString()

        Glide.with(this@MangaActivity)
            .load(imageUrl)
            .into(mangaImageView)

        mangaTitle.text = mangaResponse.data?.title
        mangaJapaneseTitle.text = mangaResponse.data?.titleJapanese
        mangaSynopsis.text = mangaResponse.data?.synopsis
        mangaChapters.text = mangaResponse.data?.chapters.toString()
        mangaStatus.text = mangaResponse.data?.status
        mangaVolumes.text = mangaResponse.data?.volumes.toString()
    }

    /**
     * Function that shows the anime data with an animation
     */
    private fun showMangaData() {
        mangaDataLayout.animate()
            .alpha(1f)
            .setDuration(1000)
            .setListener(null)

        mangaImageView.animate()
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
        val dbHelper = MangaDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        // If the anime is already a favorite, remove it
        if (isFavorite) {
            val selection = "${MangaEntry.COLUMN_MAL_ID} = ?" // WHERE clause
            val selectionArgs = arrayOf(intent.getIntExtra("malId", 100512).toString()) // WHERE arguments
            db.delete(MangaEntry.TABLE_NAME, selection, selectionArgs) // Delete the anime from the database
            favoriteButton.setImageResource(R.drawable.bookmark) // Change the icon
            isFavorite = false // Change the state of the anime

            // If the anime is not a favorite, add it
        } else {
            val values = ContentValues().apply { // Create a new row
                put(MangaEntry.COLUMN_MAL_ID, intent.getIntExtra("malId", 100512)) // Add the malId
                put(MangaEntry.COLUMN_TITLE, findViewById<TextView>(R.id.EnglishTitle).text.toString()) // Add the title
                put(MangaEntry.COLUMN_IMAGE_URL, imageUrl) // Add the image URL
            }
            db.insert(MangaEntry.TABLE_NAME, null, values) // Insert the new row
            favoriteButton.setImageResource(R.drawable.bookmarked) // Change the icon
            isFavorite = true // Change the state of the anime
        }

        db.close()
    }
}