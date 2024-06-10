package com.akashi.animelistdatabase.database.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.akashi.animelistdatabase.database.entry.MangaEntry

class MangaDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        /**
         * Companion object that contains the database name and version
         */
    companion object {
        private const val DATABASE_NAME = "FavoritesMangas.db"
        private const val DATABASE_VERSION = 2
    }

    /**
     * Function that creates the table in the database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val createFavoriteTable = "CREATE TABLE ${MangaEntry.TABLE_NAME}(" +
                "${MangaEntry.COLUMN_MAL_ID} INTEGER PRIMARY KEY," + // Primary key
                "${MangaEntry.COLUMN_TITLE} TEXT," + // Title of the manga
                "${MangaEntry.COLUMN_IMAGE_URL} TEXT)" // Image URL of the manga

        db.execSQL(createFavoriteTable)
    }

    /**
     * Function that upgrades the database
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${MangaEntry.TABLE_NAME}")
        onCreate(db)
    }
}