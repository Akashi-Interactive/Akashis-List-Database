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
import com.akashi.animelistdatabase.data.model.manga.Manga
import com.akashi.animelistdatabase.data.model.reponses.AnimesResponse
import com.akashi.animelistdatabase.data.model.reponses.MangasResponse
import com.akashi.animelistdatabase.data.repository.AnimeRepository
import com.akashi.animelistdatabase.data.repository.MangaRepository
import com.akashi.animelistdatabase.database.entry.AnimeEntry
import com.akashi.animelistdatabase.database.entry.MangaEntry
import com.akashi.animelistdatabase.database.helper.AnimeDatabaseHelper
import com.akashi.animelistdatabase.database.helper.MangaDatabaseHelper
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
    private lateinit var mangaRepository: MangaRepository
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var optionAnime: Button
    private lateinit var optionManga: Button
    private lateinit var optionFavoriteAnime: Button
    private lateinit var optionFavoriteManga: Button

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
        optionFavoriteManga = findViewById(R.id.option_favorite_manga)
    }

    /**
     * Set the events for the buttons and the search bar
     */
    private fun setEvents() {

        searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchCall()
                true
            } else {
                false
            }
        }

        searchButton.setOnClickListener {
            searchCall()
        }

        optionAnime.setOnClickListener { consultType = ConsultType.ANIME ; selectOption(optionAnime) }
        optionManga.setOnClickListener { consultType = ConsultType.MANGA ; selectOption(optionManga) }
        optionFavoriteAnime.setOnClickListener { consultType = ConsultType.FAVORITE_ANIME ; selectOption(optionFavoriteAnime) }
        optionFavoriteManga.setOnClickListener { consultType = ConsultType.FAVORITE_MANGA ; selectOption(optionFavoriteManga) }
    }

    /**
     * Initialize the recycler view and the repository
     */
    private fun initialize(){
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.alpha = 0f

        mangaRepository = MangaRepository()
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
            ConsultType.MANGA -> topMangaCall()
            ConsultType.FAVORITE_ANIME -> loadFavoriteAnimes()
            ConsultType.FAVORITE_MANGA -> loadFavoriteMangas()
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
            ConsultType.MANGA -> processMangaResponse(json, gson)
            ConsultType.FAVORITE_ANIME -> processAnimeResponse(json, gson)
            ConsultType.FAVORITE_MANGA -> processMangaResponse(json, gson)
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
            cardItemList.add(CardItem(title, imageUrl, malId, "Anime"))
        }

        loadCards(cardItemList)
    }

    /**
     * Process the manga response
     * @param json The response from the API
     * @param gson The Gson object to parse the json
     */
    private fun processMangaResponse(json: String, gson: Gson) {
        val mangas = gson.fromJson(json, MangasResponse::class.java).data
        val cardItemList = ArrayList<CardItem>()

        if(mangas.isNullOrEmpty()) {
            Log.d("ApiProcess", "No results found in manga response")
            return
        }

        for (manga in mangas) {
            val title = manga.title
            val imageUrl = manga.images?.jpg?.imageUrl
            val malId = manga.malId
            cardItemList.add(CardItem(title, imageUrl, malId, "Manga"))
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
     * Search for a manga by its name
     * @param query The name of the manga to search
     */
    private fun searchMangaCall(query: String){
        mangaRepository.searchManga(query).enqueue(object : Callback<ResponseBody> {
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
     * Get the top manga from the API
     */
    private fun topMangaCall(){
        mangaRepository.getTopManga().enqueue(object : Callback<ResponseBody> {
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
            cardItemList.add(CardItem(title, imageUrl, malId, "Anime"))
            Log.d("SearchActivity", "Anime found in database: ID = $malId, Title = $title, Image URL = $imageUrl")
        }

        loadCards(cardItemList)

        cursor.close()
    }

    /**
     * Load the favorite mangas from the database
     */
    private fun loadFavoriteMangas() {
        val dbHelper = MangaDatabaseHelper(this)
        val db = dbHelper.readableDatabase

        Log.d("SearchActivity", "Loading favorite mangas from database")

        val cursor = db.query(
            MangaEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val cardItemList = ArrayList<CardItem>()

        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(MangaEntry.COLUMN_TITLE))
            val imageUrl =
                cursor.getString(cursor.getColumnIndexOrThrow(MangaEntry.COLUMN_IMAGE_URL))
            val malId = cursor.getInt(cursor.getColumnIndexOrThrow(MangaEntry.COLUMN_MAL_ID))
            cardItemList.add(CardItem(title, imageUrl, malId, "Manga"))
            Log.d(
                "SearchActivity",
                "Manga found in database: ID = $malId, Title = $title, Image URL = $imageUrl"
            )
        }

        loadCards(cardItemList)

        cursor.close()
    }

    private fun searchCall(){
        if(consultType == ConsultType.ANIME)
            searchAnimeCall(searchEditText.text.toString())
        else
            searchMangaCall(searchEditText.text.toString())
    }
}