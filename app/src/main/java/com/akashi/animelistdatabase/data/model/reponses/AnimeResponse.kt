package com.akashi.animelistdatabase.data.model.reponses

import com.akashi.animelistdatabase.data.model.anime.Anime
import com.google.gson.annotations.SerializedName

/**
 * This class is used to parse the response from the API when searching for animes.
 */
class AnimeResponse {
    @SerializedName("data")
    var data: Anime? = null
}