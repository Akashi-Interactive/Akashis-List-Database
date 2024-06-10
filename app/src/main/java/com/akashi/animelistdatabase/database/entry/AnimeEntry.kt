package com.akashi.animelistdatabase.database.entry

import android.provider.BaseColumns

/**
 * Object that represents the AnimeEntry table
 */
object AnimeEntry : BaseColumns {
    const val TABLE_NAME = "favoritesAnimes"
    const val COLUMN_MAL_ID = "malId"
    const val COLUMN_TITLE = "title"
    const val COLUMN_IMAGE_URL = "imageUrl"
}