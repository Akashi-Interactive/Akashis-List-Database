package com.akashi.animelistdatabase.data.model.basics

import com.google.gson.annotations.SerializedName

class Aired {
    @SerializedName("from")
    var from: String? = null

    @SerializedName("to")
    var to: String? = null

    @SerializedName("string")
    var string: String? = null
}