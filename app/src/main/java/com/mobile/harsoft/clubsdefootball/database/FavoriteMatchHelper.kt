package com.mobile.harsoft.clubsdefootball.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mobile.harsoft.clubsdefootball.model.Favorite
import org.jetbrains.anko.db.*

class FavoriteMatchHelper(context: Context) :
    ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: FavoriteMatchHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavoriteMatchHelper {
            if (instance == null) {
                instance = FavoriteMatchHelper(ctx.applicationContext)
            }
            return instance as FavoriteMatchHelper

        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.EVENT_ID to TEXT + PRIMARY_KEY,
            Favorite.EVENT_NAME to TEXT,
            Favorite.EVENT_FILE to TEXT,
            Favorite.LEAGUE to TEXT,
            Favorite.SEASON to TEXT,
            Favorite.SPORT to TEXT,
            Favorite.ID_LEAGUE to TEXT,
            Favorite.DESCRIPTION to TEXT,
            Favorite.HOME_TEAM to TEXT,
            Favorite.AWAY_TEAM to TEXT,
            Favorite.HOME_SCORED to INTEGER,
            Favorite.AWAY_SCORED to INTEGER,
            Favorite.EVENT_DATE to TEXT,
            Favorite.EVENT_TIME to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("TABLE_FAVORITE", true)
    }
}

val Context.database: FavoriteMatchHelper
    get() = FavoriteMatchHelper.getInstance(applicationContext)