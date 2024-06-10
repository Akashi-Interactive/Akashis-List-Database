package com.akashi.animelistdatabase.database.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.akashi.animelistdatabase.database.entry.AnimeEntry

class AnimeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "FavoritesAnimes.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createFavoriteTable = "CREATE TABLE ${AnimeEntry.TABLE_NAME}(" +
                "${AnimeEntry.COLUMN_MAL_ID} INTEGER PRIMARY KEY," +
                "${AnimeEntry.COLUMN_TITLE} TEXT)"

        db.execSQL(createFavoriteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${AnimeEntry.TABLE_NAME}")
        onCreate(db)
    }
}