package com.akashi.animelistdatabase.data.model.reponses

import com.akashi.animelistdatabase.data.model.anime.Anime
import com.akashi.animelistdatabase.data.model.basics.Pagination
import com.google.gson.annotations.SerializedName

class AnimesResponse {
    @SerializedName("pagination")
    var pagination: Pagination? = null

    @SerializedName("data")
    var data: List<Anime>? = null
}