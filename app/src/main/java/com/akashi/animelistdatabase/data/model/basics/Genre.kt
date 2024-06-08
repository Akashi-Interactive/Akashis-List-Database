package com.akashi.animelistdatabase.data.model.basics

import com.google.gson.annotations.SerializedName

class Genre {
    @SerializedName("mal_id")
    var malId: Int = 0

    @SerializedName("type")
    var type: String? = null

    @SerializedName("name")
    var name: String? = null
}