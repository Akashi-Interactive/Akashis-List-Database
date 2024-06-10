package com.akashi.animelistdatabase.database.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.akashi.animelistdatabase.database.entry.AnimeEntry

class AnimeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        /**
         * Companion object that contains the database name and version
         */
    companion object {
        private const val DATABASE_NAME = "FavoritesAnimes.db"
        private const val DATABASE_VERSION = 2
    }

    /**
     * Function that creates the table in the database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val createFavoriteTable = "CREATE TABLE ${AnimeEntry.TABLE_NAME}(" +
                "${AnimeEntry.COLUMN_MAL_ID} INTEGER PRIMARY KEY," + // Primary key
                "${AnimeEntry.COLUMN_TITLE} TEXT," + // Title of the anime
                "${AnimeEntry.COLUMN_IMAGE_URL} TEXT)" // Image URL of the anime

        db.execSQL(createFavoriteTable)
    }

    /**
     * Function that upgrades the database
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${AnimeEntry.TABLE_NAME}")
        onCreate(db)
    }
}