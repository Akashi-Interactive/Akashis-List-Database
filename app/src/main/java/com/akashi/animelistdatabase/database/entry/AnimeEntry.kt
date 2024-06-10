package com.akashi.animelistdatabase.database.entry

import android.provider.BaseColumns

object AnimeEntry : BaseColumns {
    const val TABLE_NAME = "favoritesAnimes"
    const val COLUMN_MAL_ID = "malId"
    const val COLUMN_TITLE = "title"
}