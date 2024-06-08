package com.akashi.animelistdatabase.data.model.basics

import com.google.gson.annotations.SerializedName

class Items {
    @SerializedName("count")
    var count: Int = 0

    @SerializedName("total")
    var total: Int = 0

    @SerializedName("per_page")
    var perPage: Int = 0
}