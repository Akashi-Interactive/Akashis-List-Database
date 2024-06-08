package com.akashi.animelistdatabase.data.model.basics

import com.google.gson.annotations.SerializedName

class Pagination {
    @SerializedName("last_visible_page")
    var lastVisiblePage: Int = 0

    @SerializedName("has_next_page")
    var hasNextPage: Boolean = false

    @SerializedName("current_page")
    var currentPage: Int = 0

    @SerializedName("items")
    var items: Items? = null
}