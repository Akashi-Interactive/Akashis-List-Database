package com.akashi.animelistdatabase.ui.search

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashi.animelistdatabase.R
import com.akashi.animelistdatabase.data.enums.ConsultType
import com.akashi.animelistdatabase.data.model.card.CardItem
import com.akashi.animelistdatabase.data.model.reponses.AnimesResponse
import com.akashi.animelistdatabase.data.repository.AnimeRepository
import com.akashi.animelistdatabase.database.entry.AnimeEntry
import com.akashi.animelistdatabase.database.helper.AnimeDatabaseHelper
import com.google.gson.Gson
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import okhttp3.internal.connection.RouteSelector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private lateinit var animeRepository: AnimeRepository
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var optionAnime: Button
    private lateinit var optionManga: Button
    private lateinit var optionFavoriteAnime: Button

    private var consultType: ConsultType = ConsultType.ANIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initializeReferences()
        setEvents()
        initialize()

        topAnimeCall()
    }

    /**
     * Search for all elements required in the layout
     */
    private fun initializeReferences() {
        recyclerView = findViewById(R.id.recycler_view)
        searchEditText = findViewById(R.id.search_edit_text)
        searchButton = findViewById(R.id.search_button)
        optionAnime = findViewById(R.id.option_anime)
        optionManga = findViewById(R.id.option_manga)
        optionFavoriteAnime = findViewById(R.id.option_favorite_anime)
    }

    /**
     * Set the events for the buttons and the search bar
     */
    private fun setEvents() {

        searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchAnimeCall(v.text.toString())
                true
            } else {
                false
            }
        }

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            searchAnimeCall(query)
        }

        optionAnime.setOnClickListener { consultType = ConsultType.ANIME ; selectOption(optionAnime) }
        optionManga.setOnClickListener { consultType = ConsultType.MANGA ; selectOption(optionManga) }
        optionFavoriteAnime.setOnClickListener { consultType = ConsultType.FAVORITE_ANIME ; selectOption(optionFavoriteAnime) }
    }

    /**
     * Initialize the recycler view and the repository
     */
    private fun initialize(){
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.alpha = 0f

        animeRepository = AnimeRepository()
    }

    /**
     * Select the option for the search
     * @param selectedButton The button selected
     */
    private fun selectOption(selectedButton: Button) {
        optionAnime.isSelected = false
        optionManga.isSelected = false
        optionFavoriteAnime.isSelected = false

        selectedButton.isSelected = true
        Log.w("SearchActivity", "Selected option: ${selectedButton.text}")

        resetCards()

        when(consultType){
            ConsultType.ANIME -> topAnimeCall()
            ConsultType.MANGA -> TODO()
            ConsultType.FAVORITE_ANIME -> loadFavoriteAnimes()
            ConsultType.FAVORITE_MANGA -> TODO()
        }
    }

    /**
     * Process the json response from the API
     * @param json The response from the API
     */
    private fun processResponse(json: String){
        Log.w("ApiProcess", "Processing response for consult type: $consultType")

        val gson = Gson()

        when(consultType){
            ConsultType.ANIME -> processAnimeResponse(json, gson)
            ConsultType.MANGA -> TODO()
            ConsultType.FAVORITE_ANIME -> processAnimeResponse(json, gson)
            ConsultType.FAVORITE_MANGA -> TODO()
        }

        Log.w("ApiProcess", "Consult type: $consultType finished processing response")
    }

    /**
     * Process the animes response
     * @param json The response from the API
     * @param gson The Gson object to parse the json
     */
    private fun processAnimeResponse(json: String, gson: Gson) {
        val animes = gson.fromJson(json, AnimesResponse::class.java).data
        val cardItemList = ArrayList<CardItem>()

        if(animes.isNullOrEmpty()) {
            Log.d("ApiProcess", "No results found in anime response")
            return
        }

        for (anime in animes) {
            val title = anime.title
            val imageUrl = anime.images?.jpg?.imageUrl
            val malId = anime.malId
            cardItemList.add(CardItem(title, imageUrl, malId))
        }

        loadCards(cardItemList)
    }

    /**
     * Load the cards into the recycler view
     * @param cardItemList The list of cards to load
     */
    private fun loadCards(cardItemList: List<CardItem>){
        cardAdapter = CardAdapter(cardItemList, this@SearchActivity)
        recyclerView.adapter = cardAdapter

        recyclerView.animate()
            .alpha(1f) // Final alpha value (1 = opaque, 0 = invisible)
            .setDuration(1000) // Duration in milliseconds
            .setListener(null)

        // Notify the adapter that the data set has changed
        cardAdapter.notifyDataSetChanged()
    }

    /**
     * Unload the cards from the recycler view
     */
    private fun resetCards(){
        recyclerView.animate()
            .alpha(0f)
            .setDuration(250)
            .setListener(null)
    }

    /**
     * Search for an anime by its name
     * @param query The name of the anime to search
     */
    private fun searchAnimeCall(query: String){
        animeRepository.searchAnime(query).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val json = response.body()?.string()
                        Log.w("ApiCall", "Response body: $json")
                        processResponse(json!!)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("ApiCall", "Error: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ApiCall", "onFailure: ", t)
            }
        })
    }

    /**
     * Get the top anime from the API
     */
    private fun topAnimeCall(){
        animeRepository.getTopAnime().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val json = response.body()?.string()
                        Log.w("ApiCall", "Response body: $json")
                        processResponse(json!!)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("ApiCall", "Error: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ApiCall", "onFailure: ", t)
            }
        })
    }

    /**
     * Load the favorite animes from the database
     */
    private fun loadFavoriteAnimes(){
        val dbHelper = AnimeDatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            AnimeEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val cardItemList = ArrayList<CardItem>()

        while(cursor.moveToNext()){
            val title = cursor.getString(cursor.getColumnIndexOrThrow(AnimeEntry.COLUMN_TITLE))
            val imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(AnimeEntry.COLUMN_IMAGE_URL))
            val malId = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeEntry.COLUMN_MAL_ID))
            cardItemList.add(CardItem(title, imageUrl, malId))
            Log.d("SearchActivity", "Anime found in database: ID = $malId, Title = $title, Image URL = $imageUrl")
        }

        loadCards(cardItemList)

        cursor.close()
    }
}