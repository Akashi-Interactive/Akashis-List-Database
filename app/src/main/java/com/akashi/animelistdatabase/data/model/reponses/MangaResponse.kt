package com.akashi.animelistdatabase.data.model.reponses

import com.akashi.animelistdatabase.data.model.manga.Manga
import com.google.gson.annotations.SerializedName

/**
 * Object that represents the MangaResponse table
 */
class MangaResponse {
    @SerializedName("data")
    var data: Manga? = null
}