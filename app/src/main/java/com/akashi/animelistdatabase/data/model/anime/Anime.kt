package com.akashi.animelistdatabase.data.model.anime

import com.akashi.animelistdatabase.data.model.basics.Aired
import com.akashi.animelistdatabase.data.model.basics.Genre
import com.akashi.animelistdatabase.data.model.basics.Images
import com.google.gson.annotations.SerializedName

/**
 * Object that represents the Anime table
 */
class Anime {
    @SerializedName("mal_id")
    var malId: Int = 40974

    @SerializedName("url")
    var url: String = "https://myanimelist.net/anime/40974/Kuma_Kuma_Kuma_Bear"

    @SerializedName("images")
    var images: Images? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("title_english")
    var titleEnglish: String? = null

    @SerializedName("title_japanese")
    var titleJapanese: String? = null

    @SerializedName("source")
    var source: String? = null

    @SerializedName("episodes")
    var episodes: Int = 0

    @SerializedName("status")
    var status: String? = null

    @SerializedName("aired")
    var aired: Aired? = null

    @SerializedName("duration")
    var duration: String? = null

    @SerializedName("rating")
    var rating: String? = null

    @SerializedName("score")
    var score: Double = 0.0

    @SerializedName("synopsis")
    var synopsis: String? = null

    @SerializedName("genres")
    var genres: List<Genre>? = null
}