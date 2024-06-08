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
import com.akashi.animelistdatabase.data.model.card.CardItem
import com.akashi.animelistdatabase.data.model.reponses.AnimesResponse
import com.akashi.animelistdatabase.data.repository.AnimeRepository
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private lateinit var animeRepository: AnimeRepository
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.alpha = 0f

        animeRepository = AnimeRepository()

        searchEditText = findViewById(R.id.search_edit_text)

        searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                testApiCall(v.text.toString())
                true
            } else {
                false
            }
        }

        val searchButton = findViewById<Button>(R.id.search_button)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            testApiCall(query)
        }

        topanime()
    }

    private fun testApiCall(query: String) {
        animeRepository.searchAnime(query).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val json = response.body()?.string()
                        Log.d("TestApiCall", "Response body: $json")

                        val gson = Gson()
                        val animes = gson.fromJson(json, AnimesResponse::class.java).data
                        val cardItemList = ArrayList<CardItem>()

                        if(animes.isNullOrEmpty()) {
                            Log.d("TestApiCall", "No results found")
                            return
                        }

                        for (anime in animes) {
                            val title = anime.title
                            val imageUrl = anime.images?.jpg?.imageUrl
                            val malId = anime.malId
                            cardItemList.add(CardItem(title, imageUrl, malId))
                        }

                        cardAdapter = CardAdapter(cardItemList, this@SearchActivity)
                        recyclerView.adapter = cardAdapter

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("TestApiCall", "Error: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("TestApiCall", "onFailure: ", t)
            }
        })
    }

    private fun topanime() {
        animeRepository.getTopAnime().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val json = response.body()?.string()
                        Log.d("TestApiCall", "Response body: $json")

                        val gson = Gson()
                        val animes = gson.fromJson(json, AnimesResponse::class.java).data
                        val cardItemList = ArrayList<CardItem>()

                        if(animes.isNullOrEmpty()) {
                            Log.d("TestApiCall", "No results found")
                            return
                        }

                        for (anime in animes) {
                            val title = anime.title
                            val imageUrl = anime.images?.jpg?.imageUrl
                            val malId = anime.malId
                            cardItemList.add(CardItem(title, imageUrl, malId))
                        }

                        cardAdapter = CardAdapter(cardItemList, this@SearchActivity)
                        recyclerView.adapter = cardAdapter

                        recyclerView.animate()
                            .alpha(1f) // Final alpha value (1 = opaque, 0 = invisible)
                            .setDuration(1000) // Duration in milliseconds
                            .setListener(null)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Log.e("TestApiCall", "Error: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("TestApiCall", "onFailure: ", t)
            }
        })
    }
}