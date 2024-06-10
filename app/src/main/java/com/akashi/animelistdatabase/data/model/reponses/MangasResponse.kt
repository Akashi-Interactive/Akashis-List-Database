package com.akashi.animelistdatabase.data.model.reponses

import com.akashi.animelistdatabase.data.model.basics.Pagination
import com.akashi.animelistdatabase.data.model.manga.Manga
import com.google.gson.annotations.SerializedName

/**
 * This class is used to parse the response from the API when searching for mangas.
 */
class MangasResponse {
    @SerializedName("pagination")
    var pagination: Pagination? = null

    @SerializedName("data")
    var data: List<Manga>? = null
}