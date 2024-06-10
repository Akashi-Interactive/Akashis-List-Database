package com.akashi.animelistdatabase.data.model.reponses

import com.akashi.animelistdatabase.data.model.anime.Anime
import com.google.gson.annotations.SerializedName

class AnimeResponse {
    @SerializedName("data")
    var data: Anime? = null
}