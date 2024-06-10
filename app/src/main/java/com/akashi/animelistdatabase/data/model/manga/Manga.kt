package com.akashi.animelistdatabase.data.model.manga

import com.akashi.animelistdatabase.data.model.basics.Images
import com.google.gson.annotations.SerializedName

/**
 * Object that represents the Manga table
 */
class Manga {
    @SerializedName("mal_id")
    var malId: Int = 100512

    @SerializedName("images")
    var images: Images? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("title_english")
    var titleEnglish: String? = null

    @SerializedName("title_japanese")
    var titleJapanese: String? = null

    @SerializedName("chapters")
    var chapters: Int = 0

    @SerializedName("volumes")
    var volumes: Int = 0

    @SerializedName("status")
    var status: String? = null

    @SerializedName("synopsis")
    var synopsis: String? = null
}